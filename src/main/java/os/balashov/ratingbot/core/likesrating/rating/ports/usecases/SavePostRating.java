package os.balashov.ratingbot.core.likesrating.rating.ports.usecases;

import org.springframework.scheduling.annotation.Async;
import os.balashov.ratingbot.core.common.application.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.rating.ports.dtos.PostRating;

public interface SavePostRating {
    @Async
    @Loggable(message = "Use case: Try to save post rating {4} for message {1} in chat {2}")
    void save(int messageId, long chatId, PostRating postRating);
}
