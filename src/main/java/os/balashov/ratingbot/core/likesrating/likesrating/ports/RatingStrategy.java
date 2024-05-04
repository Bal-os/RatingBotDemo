package os.balashov.ratingbot.core.likesrating.likesrating.ports;

public interface RatingStrategy {
    double calculateRating(int likes, int dislikes, int totalUsers);
}
