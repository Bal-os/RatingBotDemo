package os.balashov.ratingbot.core.likesrating.calculate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ExponentialInclinationCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ports.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.ports.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.TangentsInclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.ports.entity.RatingParameters;
import os.balashov.ratingbot.core.likesrating.calculate.strategies.InclinedParameterizedRatingStrategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class InclinedParameterizedRatingStrategyTest {
    private InclinedRatingCalculator inclinationCalculator;
    private InclinationCalculator ratingCalculator;

    @BeforeEach
    public void setUp() {
        ratingCalculator = new ExponentialInclinationCalculator();
        inclinationCalculator = new TangentsInclinedRatingCalculator();
    }

    @Test
    public void testCalculateRatingIntegration() {
        RatingParameters parameters = new RatingParameters(1, 0, 100, 2);

        InclinedParameterizedRatingStrategy strategy = new InclinedParameterizedRatingStrategy(
                inclinationCalculator, ratingCalculator, parameters);

        int totalUsers = 200;
        int likes = 120;
        int dislikes = 80;
        double result = strategy.calculateRating(likes, dislikes, totalUsers);

        assertTrue("Result should be between 0 and 100", result >= 0 && result <= 100);

        double expectedInclination = ratingCalculator.calculate(parameters.inclinationFactor());
        double expectedValue = inclinationCalculator.calculate(likes, dislikes, totalUsers, expectedInclination);
        double expected = (parameters.upperBound() - parameters.lowerBound()) * expectedValue + parameters.lowerBound();
        assertEquals(expected, result, 0.01);
    }

    @Test
    public void testCalculateRating() {
        inclinationCalculator = mock(InclinedRatingCalculator.class);
        ratingCalculator = mock(InclinationCalculator.class);
        RatingParameters parameters = new RatingParameters(10, 0, 100, 2);
        InclinedParameterizedRatingStrategy strategy = new InclinedParameterizedRatingStrategy(inclinationCalculator, ratingCalculator, parameters);
        when(ratingCalculator.calculate(10)).thenReturn(0.5);
        when(inclinationCalculator.calculate(150, 50, 300, 0.5)).thenReturn(0.75);

        double result = strategy.calculateRating(150, 50, 300);
        assertEquals(75.0, result, 0.001);

        verify(ratingCalculator).calculate(10);
        verify(inclinationCalculator).calculate(150, 50, 300, 0.5);
    }

    @Test
    public void testConcurrencyInRatingCalculations() throws InterruptedException {
        int numberOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        AtomicBoolean failed = new AtomicBoolean(false);

        RatingParameters parameters = new RatingParameters(1, 0, 100, 2);
        InclinedParameterizedRatingStrategy strategy = new InclinedParameterizedRatingStrategy(
                inclinationCalculator, ratingCalculator, parameters);

        // Повторяющийся тест в многих потоках
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    double result = strategy.calculateRating(120, 80, 200);
                    if (!(result >= 0 && result <= 100)) {
                        failed.set(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    failed.set(true);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        assertFalse("There should be no failures in concurrent execution.", failed.get());
    }
}
