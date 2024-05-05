package os.balashov.ratingbot.core.likesrating.ports;

import os.balashov.ratingbot.core.common.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;

public interface CreatePostRating {
    @Loggable(message = "Service: Try to create empty post rating")
    PostRating createEmpty();

    @Loggable(message = "Service: Try to create post rating with likes {1}, dislikes {2} and rating {3}")
    PostRating create(int likes, int dislikes, double rating);
}
