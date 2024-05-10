package os.balashov.ratingbot.core.likesrating.calculate;

import org.junit.jupiter.api.Test;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.TangentsInclinedRatingCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class TangentsInclinedRatingCalculatorTest {
    private static final double DELTA = 0.0001;

    private static double getExpected(double inclination, int likes, int dislikes, int totalUsers) {
        double dislikeFactor = normalize(dislikes - (double) totalUsers / 2);
        double dislikeValue = 0.5 - 0.5 * dislikeFactor;
        double likesFactor = normalize(likes - dislikes);
        double likesDislikesValue = dislikeValue + (1 - dislikeValue) * likesFactor * inclination;
        double balance = (double) (likes - dislikes) / totalUsers;
        double normalizedBalance = 0.5 + 0.5 * balance;
        double balanceDifference = likesDislikesValue - normalizedBalance;
        return likesDislikesValue - inclination * normalize(balanceDifference * 2 - 1) / 2;
    }

    private static double normalize(double value) {
        return 0.5 * (Math.tanh(value) + 1);
    }

    @Test
    public void testStandardUsage() {
        TangentsInclinedRatingCalculator calculator = new TangentsInclinedRatingCalculator();
        double inclination = 1.0;
        int likes = 150, dislikes = 100, totalUsers = 300;

        double expected = getExpected(inclination, likes, dislikes, totalUsers);
        double actual = calculator.calculate(likes, dislikes, totalUsers, inclination);

        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testZeroLikesAndDislikes() {
        TangentsInclinedRatingCalculator calculator = new TangentsInclinedRatingCalculator();
        double inclination = 1.0;
        int likes = 0, dislikes = 0, totalUsers = 100;

        double expected = getExpected(inclination, likes, dislikes, totalUsers);
        double actual = calculator.calculate(likes, dislikes, totalUsers, inclination);

        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testExtremeHighInclination() {
        TangentsInclinedRatingCalculator calculator = new TangentsInclinedRatingCalculator();
        double inclination = 1000.0;
        int likes = 100, dislikes = 50, totalUsers = 200;

        double expected = getExpected(inclination, likes, dislikes, totalUsers);
        double actual = calculator.calculate(likes, dislikes, totalUsers, inclination);

        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testNegativeInclination() {
        TangentsInclinedRatingCalculator calculator = new TangentsInclinedRatingCalculator();
        double inclination = -1.0;
        int likes = 100, dislikes = 50, totalUsers = 200;

        double expected = getExpected(inclination, likes, dislikes, totalUsers);
        double actual = calculator.calculate(likes, dislikes, totalUsers, inclination);

        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testResultWithinBounds() {
        TangentsInclinedRatingCalculator calculator = new TangentsInclinedRatingCalculator();
        double inclination = 1.0;
        int likes = 200, dislikes = 100, totalUsers = 500;

        double actual = calculator.calculate(likes, dislikes, totalUsers, inclination);
        assertTrue("Result should be within [0,1]", actual >= 0 && actual <= 1);
    }
}
