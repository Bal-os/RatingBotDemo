package os.balashov.ratingbot.infrastructure.sql.adapters;

import jakarta.transaction.Transactional;
import os.balashov.ratingbot.core.common.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;
import os.balashov.ratingbot.infrastructure.sql.entities.MessageKey;

public interface RatingSaver {
    @Transactional
    @Loggable(message = "SqlAdapter: Save rating {2}, associated with user {3}")
    void saveRating(MessageKey key, PostRating rating, Long userId, Marks vote);
}
