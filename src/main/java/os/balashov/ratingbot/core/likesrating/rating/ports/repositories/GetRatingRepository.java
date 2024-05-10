package os.balashov.ratingbot.core.likesrating.rating.ports.repositories;

import os.balashov.ratingbot.core.common.application.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.rating.ports.dtos.PostRating;

import java.util.Optional;

public interface GetRatingRepository {
    @Loggable(message = "Repository: Try to get rating for message {1} from chat {2} from database")
    Optional<PostRating> getRating(int messageId, long chatId);
}
