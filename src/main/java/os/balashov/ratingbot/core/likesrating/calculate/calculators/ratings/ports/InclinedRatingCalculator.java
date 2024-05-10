package os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.ports;

@FunctionalInterface
public interface InclinedRatingCalculator {
    double calculate(int likes, int dislikes, int totalUsers, double inclination);
}
