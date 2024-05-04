package os.balashov.ratingbot.core.likesrating.likesrating.calculators.ratings;

@FunctionalInterface
public interface InclinedRatingCalculator {
    double calculate(int likes, int dislikes, int totalUsers, double inclination);
}
