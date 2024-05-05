package os.balashov.ratingbot.core.likesrating.domain.ports;

public interface RatingStrategy {
    double calculateRating(int likes, int dislikes, int totalUsers);
}
