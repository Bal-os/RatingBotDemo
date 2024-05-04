package os.balashov.ratingbot.core.likesrating.application.repositories;

import os.balashov.ratingbot.core.common.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;

import java.util.Optional;

public interface GetRatingRepository {
    @Loggable(message = "Repository: Try to get rating for message {1} from chat {2} from database")
    Optional<PostRating> getRating(int messageId, long chatId);
}
