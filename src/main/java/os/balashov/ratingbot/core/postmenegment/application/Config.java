package os.balashov.ratingbot.core.postmenegment.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import os.balashov.ratingbot.core.postmenegment.ports.usecases.ProcessPostCreation;
import os.balashov.ratingbot.core.postmenegment.ports.events.SavePostEvent;
import os.balashov.ratingbot.core.postmenegment.ports.usecases.UpdatePost;

import java.util.Optional;

@Configuration
public class Config {
    @Bean
    public ProcessPostCreation processPostCreation(ApplicationEventPublisher applicationEventPublisher) {
        return (messageId, chatId, userId, text) -> Optional.of(new SavePostEvent(this, messageId, chatId, userId, text))
                .ifPresent(applicationEventPublisher::publishEvent);
    }

    @Bean
    public UpdatePost updatePost(ApplicationEventPublisher applicationEventPublisher) {
        return (messageId, chatId, userId, text) -> Optional.of(new SavePostEvent(this, messageId, chatId, userId, text))
                .ifPresent(applicationEventPublisher::publishEvent);
    }
}
