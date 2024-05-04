package os.balashov.ratingbot.core.likesrating.application.repositories;

import os.balashov.ratingbot.core.common.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;

public interface SaveRatingRepository {
    @Loggable(message = "Repository: Try to save rating {4} for message {1} in chat {2} to database")
    void saveRating(int messageId, long chatId, PostRating postRating);
}
