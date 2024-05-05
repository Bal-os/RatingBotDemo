package os.balashov.ratingbot.core.likesrating.application.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import os.balashov.ratingbot.core.likesrating.application.events.SaveRatingEvent;
import os.balashov.ratingbot.core.likesrating.application.events.SaveVoteEvent;
import os.balashov.ratingbot.core.likesrating.application.repositories.GetRatingRepository;
import os.balashov.ratingbot.core.likesrating.application.usecases.FindPostRating;
import os.balashov.ratingbot.core.likesrating.application.usecases.SavePostRating;
import os.balashov.ratingbot.core.likesrating.application.usecases.SaveUserVote;

import java.util.Optional;


@Configuration
public class ApplicationConfig {
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
    public FindPostRating findPostRating(GetRatingRepository getRatingRepository) {
        return getRatingRepository::getRating;
    }
}
