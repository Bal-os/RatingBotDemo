package os.balashov.ratingbot.core.likesrating.domain.ports;

public interface CalculateRating {
    double calculateRating(int likes, int dislikes, int totalUsers);
}
