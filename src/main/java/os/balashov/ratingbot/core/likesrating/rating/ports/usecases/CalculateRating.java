package os.balashov.ratingbot.core.likesrating.rating.ports.usecases;

public interface CalculateRating {
    double calculateRating(int likes, int dislikes, int totalUsers);
}
