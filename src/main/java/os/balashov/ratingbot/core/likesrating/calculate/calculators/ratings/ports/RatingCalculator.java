package os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.ports;

@FunctionalInterface
public interface RatingCalculator {
    double calculate(int likes, int dislikes, int totalUsers);
}
