package os.balashov.ratingbot.core.likesrating.rating.ports.usecases;

import os.balashov.ratingbot.core.common.application.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.rating.ports.dtos.PostRating;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;

public interface ChangePostRating {
    @Loggable(message = "Use case: Try to swap post rating in-memory")
    PostRating swapRatingCounts(Marks markToIncrement, PostRating postRating);

    @Loggable(message = "Use case: Try to increment rating counts in-memory")
    PostRating incrementRatingCounts(Marks markToIncrement, PostRating postRating);

    @Loggable(message = "Use case: Try to set new rating {4} for post rating in-memory")
    default PostRating setNewRating(PostRating postRating, double rating) {
        postRating.setRating(rating);
        return postRating;
    }
}
