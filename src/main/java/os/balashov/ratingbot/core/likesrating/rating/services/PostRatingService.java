package os.balashov.ratingbot.core.likesrating.rating.services;

import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.likesrating.rating.ports.dtos.PostRating;
import os.balashov.ratingbot.core.likesrating.rating.ports.usecases.ChangePostRating;
import os.balashov.ratingbot.core.likesrating.rating.ports.usecases.CreatePostRating;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;

@Service
public class PostRatingService implements CreatePostRating, ChangePostRating {
    @Override
    public PostRating createEmpty() {
        return PostRating.create(0, 0, 0);
    }

    @Override
    public PostRating create(int likes, int dislikes, double rating) {
        return PostRating.create(likes, dislikes, rating);
    }

    @Override
    public PostRating swapRatingCounts(Marks markToIncrement, PostRating postRating) {
        if (Marks.LIKE.equals(markToIncrement)) {
            postRating.decrementDislikes();
        } else {
            postRating.decrementLikes();
        }
        return incrementRatingCounts(markToIncrement, postRating);
    }

    @Override
    public PostRating incrementRatingCounts(Marks markToIncrement, PostRating postRating) {
        if (Marks.LIKE.equals(markToIncrement)) {
            postRating.incrementLikes();
        } else {
            postRating.incrementDislikes();
        }
        return postRating;
    }
}
