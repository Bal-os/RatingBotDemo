package os.balashov.ratingbot.core.likesrating.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveRatingEvent;
import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveVoteEvent;
import os.balashov.ratingbot.core.likesrating.rating.ports.usecases.SavePostRating;
import os.balashov.ratingbot.core.likesrating.votes.ports.usecases.SaveUserVote;
import os.balashov.ratingbot.core.postmenegment.ports.events.SavePostEvent;
import os.balashov.ratingbot.core.postmenegment.ports.events.SavePostEventListener;
import os.balashov.ratingbot.core.postmenegment.ports.repository.SavePostRepository;

import java.util.Optional;

@Configuration
public class EventsConfiguration {
    @Bean
    public SaveUserVote saveUserVote(ApplicationEventPublisher applicationEventPublisher) {
        return (messageId, chatId, userId, vote) ->
                Optional.of(new SaveVoteEvent(this, messageId, chatId, userId, vote))
                        .ifPresent(applicationEventPublisher::publishEvent);
    }

    @Bean
    public SavePostRating savePostRating(ApplicationEventPublisher applicationEventPublisher) {
        return (messageId, chatId, postRating) ->
                Optional.of(new SaveRatingEvent(this, messageId, chatId, postRating))
                        .ifPresent(applicationEventPublisher::publishEvent);
    }

    @Bean
    public SavePostEventListener postEventListener(SavePostRepository repository) {
        return (SavePostEvent event) -> repository.savePost(event.getMessageId(), event.getChatId(), event.getUserId(), event.getText());
    }
}
