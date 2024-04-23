package os.balashov.ratingbot.core.likesrating.domain.strategies;

public interface RatingStrategy {
    double calculateRating(int totalUsers, int likes, int dislikes);
}
