package os.balashov.ratingbot.infrastructure.sql.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import os.balashov.ratingbot.core.common.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.application.events.SaveRatingEvent;
import os.balashov.ratingbot.core.likesrating.application.events.SaveVoteEvent;
import os.balashov.ratingbot.core.likesrating.application.listeners.SaveRatingEventListener;
import os.balashov.ratingbot.core.likesrating.application.listeners.SaveVoteEventListener;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;
import os.balashov.ratingbot.infrastructure.sql.entities.MessageKey;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@RequiredArgsConstructor
public class RatingVoteListener implements SaveRatingEventListener, SaveVoteEventListener {
    private final ConcurrentMap<MessageKey, PostRating> latestRatings = new ConcurrentHashMap<>();
    private final ConcurrentMap<MessageKey, ConcurrentMap<Long, SaveVoteEvent>> voteEventsQueue = new ConcurrentHashMap<>();
    private final RatingSaver ratingSaver;

    @Async
    @Override
    @EventListener
    public void handleSaveRatingEvent(SaveRatingEvent event) {
        MessageKey messageKey = new MessageKey(event.getMessageId(), event.getChatId());
        PostRating rating = event.getPostRating();
        latestRatings.put(messageKey, rating);
        processVotesIfExist(messageKey);
    }

    @Async
    @Override
    @EventListener
    public void handleSaveVoteEvent(SaveVoteEvent event) {
        MessageKey messageKey = new MessageKey(event.getMessageId(), event.getChatId());
        addVoteEventToQueue(messageKey, event);
        if (latestRatings.containsKey(messageKey)) {
            processVotesIfExist(messageKey);
        }
    }

    @Loggable(message = "EventListener: add vote event {1} to queue")
    private void addVoteEventToQueue(MessageKey messageKey, SaveVoteEvent event) {
        voteEventsQueue.computeIfAbsent(messageKey, key -> new ConcurrentHashMap<>())
                .compute(event.getUserId(), (userId, oldEvent) -> getLatestEvent(oldEvent, event));
    }


    @Loggable(message = "EventListener: process votes for message {1}, if exist")
    private void processVotesIfExist(MessageKey messageKey) {
        Optional.ofNullable(voteEventsQueue.remove(messageKey))
                .filter(voteEvents -> !voteEvents.isEmpty())
                .ifPresent(voteEvents -> saveVotesData(messageKey, latestRatings.remove(messageKey), voteEvents));
    }

    @Loggable(message = "EventListener: save votes with rating {2}")
    private void saveVotesData(MessageKey key, PostRating rating, Map<Long, SaveVoteEvent> voteEventsList) {
        voteEventsList.forEach((userId, votes) -> ratingSaver.saveRating(key, rating, userId, votes.getVote()));
    }

    private SaveVoteEvent getLatestEvent(SaveVoteEvent oldEvent, SaveVoteEvent newEvent) {
        return oldEvent == null || oldEvent.getTimestamp() < newEvent.getTimestamp() ? newEvent : oldEvent;
    }
}