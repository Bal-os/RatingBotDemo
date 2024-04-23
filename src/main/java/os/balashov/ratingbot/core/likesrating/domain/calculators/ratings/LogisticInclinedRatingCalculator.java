package os.balashov.ratingbot.core.likesrating.domain.calculators.ratings;

public final class LogisticInclinedRatingCalculator implements InclinedRatingCalculator {
    public double calculate(int likes, int dislikes, int totalUsers, double inclination) {
        double exponent = inclination * (likes - dislikes + totalUsers);
        return 1 / (1 + Math.exp(exponent));
    }
}
