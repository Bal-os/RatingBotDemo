package os.balashov.ratingbot.core.likesrating.domain.calculators.ratings;

@FunctionalInterface
public interface RatingCalculator {
    double calculate(int likes, int dislikes, int totalUsers);
}
