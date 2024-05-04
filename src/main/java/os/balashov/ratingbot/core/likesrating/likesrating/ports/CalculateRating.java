package os.balashov.ratingbot.core.likesrating.likesrating.ports;

public interface CalculateRating {
    double calculateRating(int likes, int dislikes, int totalUsers);
}
