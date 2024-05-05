package os.balashov.ratingbot.infrastructure.sql.adapters;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.common.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.application.events.SaveRatingEvent;
import os.balashov.ratingbot.core.likesrating.application.events.SaveVoteEvent;
import os.balashov.ratingbot.core.likesrating.application.listeners.SaveRatingEventListener;
import os.balashov.ratingbot.core.likesrating.application.listeners.SaveVoteEvenListener;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;
import os.balashov.ratingbot.infrastructure.sql.entities.MessageKey;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class RatingVoteListener implements SaveRatingEventListener, SaveVoteEvenListener {
    private final ConcurrentMap<MessageKey, PostRating> latestRating;
    private final ConcurrentMap<MessageKey, List<SaveVoteEvent>> voteEvents;
    private final RatingSaver ratingSaver;

    public RatingVoteListener(RatingSaver ratingSaver) {
        this.ratingSaver = ratingSaver;
        this.latestRating = new ConcurrentHashMap<>();
        this.voteEvents = new ConcurrentHashMap<>();
    }

    @Async
    @Override
    @EventListener
    public void handleSaveRatingEvent(SaveRatingEvent event) {
        MessageKey key = new MessageKey(event.getMessageId(), event.getChatId());
        PostRating rating = event.getPostRating();
        latestRating.put(key, rating);
        saveVotesWithIfExists(key, rating);
    }

    @Async
    @Override
    @EventListener
    public void handleSaveVoteEvent(SaveVoteEvent event) {
        MessageKey messageKey = new MessageKey(event.getMessageId(), event.getChatId());
        voteEvents.computeIfAbsent(messageKey, key -> new LinkedList<>()).add(event);
        latestRating.computeIfPresent(messageKey, this::saveVotesWithIfExists);
    }

    @Loggable(message = "EventListener: try to save votes with rating {2}, with previous check for votes existence")
    private PostRating saveVotesWithIfExists(MessageKey key, PostRating rating) {
        Optional.ofNullable(voteEvents.remove(key))
                .filter(voteEventsList -> !voteEventsList.isEmpty())
                .ifPresent(voteEventsList -> saveData(key, rating, voteEventsList));
        return null;
    }

    @Loggable(message = "EventListener: save votes with rating {2}")
    private void saveData(MessageKey key, PostRating rating, List<SaveVoteEvent> voteEventsList) {
        var votesPerUserCollector = Collectors.groupingBy(SaveVoteEvent::getUserId, Collectors.mapping(SaveVoteEvent::getVote, Collectors.toSet()));
        voteEventsList.stream()
                .collect(votesPerUserCollector)
                .forEach((user, votes) -> ratingSaver.saveRating(key, rating, user, votes.stream().toList()));
    }
}
