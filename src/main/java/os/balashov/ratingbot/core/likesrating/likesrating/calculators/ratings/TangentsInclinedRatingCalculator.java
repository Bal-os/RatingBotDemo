package os.balashov.ratingbot.core.likesrating.likesrating.calculators.ratings;

public final class TangentsInclinedRatingCalculator implements InclinedRatingCalculator {
    public double calculate(int likes, int dislikes, int totalUsers, double inclination) {
        double dislikeFactor = normalize(dislikes - (double) totalUsers / 2);
        double dislikeValue = 0.5 - 0.5 * dislikeFactor;
        double likesFactor = normalize(likes - dislikes);
        double likesDislikesValue = dislikeValue + (1 - dislikeValue) * likesFactor * inclination;
        double balance = (double) (likes - dislikes) / totalUsers;
        double normalizedBalance = 0.5 + 0.5 * balance;
        double balanceDifference = likesDislikesValue - normalizedBalance;
        return likesDislikesValue - inclination * normalize(balanceDifference * 2 - 1) / 2;
    }

    private double normalize(double value) {
        return 0.5 * (Math.tanh(value) + 1);
    }
}
