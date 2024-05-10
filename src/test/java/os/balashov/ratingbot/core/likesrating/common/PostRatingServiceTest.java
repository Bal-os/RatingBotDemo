package os.balashov.ratingbot.core.likesrating.common;

import org.junit.jupiter.api.Test;
import os.balashov.ratingbot.core.likesrating.rating.services.PostRatingService;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;
import os.balashov.ratingbot.core.likesrating.rating.ports.dtos.PostRating;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostRatingServiceTest {
    @Test
    public void testCreateEmpty() {
        PostRatingService service = new PostRatingService();
        PostRating rating = service.createEmpty();

        assertEquals(0, rating.likes());
        assertEquals(0, rating.dislikes());
        assertEquals(0.0, rating.rating(), 0.001);
    }

    @Test
    public void testCreateWithValues() {
        PostRatingService service = new PostRatingService();
        int likes = 10;
        int dislikes = 5;
        double rating = 3.8;

        PostRating ratingObject = service.create(likes, dislikes, rating);

        assertEquals(likes, ratingObject.likes());
        assertEquals(dislikes, ratingObject.dislikes());
        assertEquals(rating, ratingObject.rating(), 0.001);
    }


    @Test
    public void testSwapRatingCounts_Like() {
        PostRatingService service = new PostRatingService();
        PostRating initialRating = PostRating.create(5, 2, 0.0);

        PostRating updatedRating = service.swapRatingCounts(Marks.LIKE, initialRating);

        assertEquals(6, updatedRating.likes());
        assertEquals(1, updatedRating.dislikes());
    }

    @Test
    public void testSwapRatingCounts_Dislike() {
        PostRatingService service = new PostRatingService();
        PostRating initialRating = PostRating.create(3, 8, 0.0);

        PostRating updatedRating = service.swapRatingCounts(Marks.DISLIKE, initialRating);

        assertEquals(2, updatedRating.likes());
        assertEquals(9, updatedRating.dislikes());
    }

    @Test
    public void testIncrementRatingCounts_Like() {
        PostRatingService service = new PostRatingService();
        PostRating initialRating = PostRating.create(12, 7, 0.0);

        PostRating updatedRating = service.incrementRatingCounts(Marks.LIKE, initialRating);

        assertEquals(13, updatedRating.likes());
        assertEquals(7, updatedRating.dislikes());
    }

    @Test
    public void testIncrementRatingCounts_Dislike() {
        PostRatingService service = new PostRatingService();
        PostRating initialRating = PostRating.create(4, 15, 0.0);

        PostRating updatedRating = service.incrementRatingCounts(Marks.DISLIKE, initialRating);

        assertEquals(4, updatedRating.likes());
        assertEquals(16, updatedRating.dislikes());
    }
}
