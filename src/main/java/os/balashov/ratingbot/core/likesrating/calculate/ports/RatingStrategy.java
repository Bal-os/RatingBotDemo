package os.balashov.ratingbot.core.likesrating.calculate.ports;

public interface RatingStrategy {
    double calculateRating(int likes, int dislikes, int totalUsers);
}
