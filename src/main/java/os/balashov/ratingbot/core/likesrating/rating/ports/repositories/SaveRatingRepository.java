package os.balashov.ratingbot.core.likesrating.rating.ports.repositories;

import jakarta.transaction.Transactional;
import os.balashov.ratingbot.core.common.application.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;
import os.balashov.ratingbot.core.likesrating.rating.ports.dtos.PostRating;

public interface SaveRatingRepository {
    @Transactional
    @Loggable(message = "Repository: Save rating change {4}, associated with user {3} vote {5}")
    void saveRating(int messageId, long chatId, long userId, PostRating rating, Marks vote);
}
