package os.balashov.ratingbot.core.likesrating.ports.dtos;

import java.util.concurrent.atomic.AtomicInteger;

public class PostRating {
    private final AtomicInteger likes;
    private final AtomicInteger dislikes;
    private volatile double rating;

    private PostRating(AtomicInteger likes, AtomicInteger dislikes, double rating) {
        this.likes = likes;
        this.dislikes = dislikes;
        this.rating = rating;
    }

    public static PostRating create(int likes, int dislikes, double rating) {
        return new PostRating(new AtomicInteger(likes), new AtomicInteger(dislikes), rating);
    }

    public int likes() {
        return likes.get();
    }

    public int dislikes() {
        return dislikes.get();
    }

    public void incrementLikes() {
        likes.incrementAndGet();
    }

    public void incrementDislikes() {
        dislikes.incrementAndGet();
    }

    public void decrementLikes() {
        likes.decrementAndGet();
    }

    public void decrementDislikes() {
        dislikes.decrementAndGet();
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double rating() {
        return rating;
    }
}
