package os.balashov.ratingbot.core.likesrating.application.usecases;

import os.balashov.ratingbot.core.common.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;

import java.util.Optional;

public interface FindPostRating {
    @Loggable(message = "Use case: Try to find post rating for message {1} in chat {2}")
    Optional<PostRating> find(int messageId, long chatId);
}
