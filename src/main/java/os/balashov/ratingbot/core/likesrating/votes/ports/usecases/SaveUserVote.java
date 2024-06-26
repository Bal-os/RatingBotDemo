package os.balashov.ratingbot.core.likesrating.votes.ports.usecases;

import org.springframework.scheduling.annotation.Async;
import os.balashov.ratingbot.core.common.application.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;

public interface SaveUserVote {
    @Async
    @Loggable(message = "Use case: Try to save user {3} vote {4} to message {1} in chat {2}")
    void save(int messageId, long chatId, long userId, Marks vote);
}
