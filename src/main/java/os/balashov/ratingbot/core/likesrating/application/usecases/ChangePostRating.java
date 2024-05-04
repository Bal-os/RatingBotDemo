package os.balashov.ratingbot.core.likesrating.application.usecases;

import os.balashov.ratingbot.core.common.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;

public interface ChangePostRating {
    @Loggable(message = "Service: Try to swap post rating in-memory")
    default PostRating swapRatingCounts(Marks markToIncrement, PostRating postRating) {
        if (Marks.LIKE.equals(markToIncrement)) {
            postRating.decrementDislikes();
        } else {
            postRating.decrementLikes();
        }
        return incrementRatingCounts(markToIncrement, postRating);
    }

    @Loggable(message = "Service: Try to increment rating counts in-memory")
    default PostRating incrementRatingCounts(Marks markToIncrement, PostRating postRating) {
        if (Marks.LIKE.equals(markToIncrement)) {
            postRating.incrementLikes();
        } else {
            postRating.incrementDislikes();
        }
        return postRating;
    }

    @Loggable(message = "Service: Try to set new rating {4} for post rating in-memory")
    default PostRating setNewRating(PostRating postRating, double rating) {
        postRating.setRating(rating);
        return postRating;
    }
}
