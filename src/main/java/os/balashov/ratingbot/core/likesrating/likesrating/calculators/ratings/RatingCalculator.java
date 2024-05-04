package os.balashov.ratingbot.core.likesrating.likesrating.calculators.ratings;

@FunctionalInterface
public interface RatingCalculator {
    double calculate(int likes, int dislikes, int totalUsers);
}
