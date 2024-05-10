package os.balashov.ratingbot.core.likesrating.events;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.common.application.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveRatingEvent;
import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveVoteEvent;
import os.balashov.ratingbot.core.likesrating.events.ports.listeners.SaveRatingEventListener;
import os.balashov.ratingbot.core.likesrating.events.ports.listeners.SaveVoteEventListener;
import os.balashov.ratingbot.core.likesrating.events.ports.services.SaveRatingEventsService;
import os.balashov.ratingbot.core.likesrating.events.ports.services.VoteEventsQueueService;
import os.balashov.ratingbot.core.likesrating.rating.ports.dtos.PostRating;
import os.balashov.ratingbot.core.likesrating.rating.ports.repositories.SaveRatingRepository;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingVoteListener implements SaveRatingEventListener, SaveVoteEventListener {
    private final SaveRatingRepository repository;
    private final SaveRatingEventsService ratingService;
    private final VoteEventsQueueService voteService;

    @Override
    public void handleSaveRatingEvent(SaveRatingEvent event) {
        var messageId = event.getMessageId();
        var chatId = event.getChatId();
        var rating = event.getPostRating();

        Optional.ofNullable(voteService.removeVoteEventsQueue(messageId, chatId)).ifPresentOrElse(
                voteEvents -> saveVotesData(messageId, chatId, rating, voteEvents),
                () -> ratingService.saveLatestEvent(messageId, chatId, event));
    }

    @Override
    public void handleSaveVoteEvent(SaveVoteEvent event) {
        var messageId = event.getMessageId();
        var chatId = event.getChatId();

        var votes = voteService.addVoteEventToQueue(messageId, chatId, event, this::getLatestEvent);
        Optional.ofNullable(ratingService.getEvent(messageId, chatId))
                .map(SaveRatingEvent::getPostRating)
                .ifPresent(rating -> saveVotesData(messageId, chatId, rating, votes));
    }

    @Loggable(message = "EventListener: save votes with rating {2}")
    private void saveVotesData(int messageId, long chatId, PostRating rating, Map<Long, SaveVoteEvent> voteEventsList) {
        voteEventsList.forEach((userId, event) -> repository.saveRating(messageId, chatId, userId, rating, event.getVote()));
        ratingService.removeLatestEvent(messageId, chatId);
        voteService.removeVoteEventsQueue(messageId, chatId);
    }

    private SaveVoteEvent getLatestEvent(SaveVoteEvent oldEvent, SaveVoteEvent newEvent) {
        return oldEvent == null || oldEvent.getTimestamp() < newEvent.getTimestamp() ? newEvent : oldEvent;
    }
}
