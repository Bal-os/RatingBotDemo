package os.balashov.ratingbot.core;

import org.junit.jupiter.api.Test;
import os.balashov.ratingbot.core.likesrating.domain.models.values.RatingParameters;
import os.balashov.ratingbot.core.likesrating.domain.services.RatingCalculatorImpl;
import os.balashov.ratingbot.core.likesrating.ports.NormalDistributionCalculator;
import os.balashov.ratingbot.core.likesrating.ports.RatingCalculator;

import java.util.Map;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class RatingCalculatorTest {
    private final NormalDistributionCalculator mockDistribution = mock(NormalDistributionCalculator.class);

    @Test
    public void testEmptyDataSet() {
        RatingParameters params = new RatingParameters(1.0, 1.0, 0.0, 5.0, 2);
        RatingCalculatorImpl calculator = new RatingCalculatorImpl(mockDistribution, params);

        double rating = calculator.calculate(0, 0, 0.0);

        assertEquals(0.0, rating, 0.0);
        verify(mockDistribution, times(0)).calculate(0.0, 0.0);
    }

    @Test
    public void testLikesAndDislikes() {
        RatingParameters params = new RatingParameters(1.0, 1.0, 0.0, 5.0, 2);
        RatingCalculatorImpl calculator = new RatingCalculatorImpl(mockDistribution, params);

        int likes = 100;
        int dislikes = 50;
        int totalVotes = likes + dislikes;
        double totalPopulation = 1000;

        double mockDistributionResult = 0.5;
        double averageNorm = getAverageNorm(likes, dislikes, totalVotes, params);
        double sigma = calculateSigma(totalVotes, totalPopulation);
        double expectedRating = calculateNormalDistributionRating(mockDistributionResult, params);
        when(mockDistribution.calculate(averageNorm, sigma)).thenReturn(mockDistributionResult);

        double rating = calculator.calculate(likes, dislikes, totalPopulation);

        assertEquals(expectedRating, rating, 0.01);
        verify(mockDistribution, times(1)).calculate(averageNorm, sigma);
    }

    @Test
    public void testOnlyDislikes() {
        RatingParameters params = new RatingParameters(1.0, 1.0, 0.0, 5.0, 2);
        RatingCalculatorImpl calculator = new RatingCalculatorImpl(mockDistribution, params);

        int likes = 0;
        int dislikes = 100;
        double totalPopulation = 1000;
        double expectedRating = calculateExponentialRating(dislikes, totalPopulation, params);

        double rating = calculator.calculate(likes, dislikes, totalPopulation);

        assertEquals(expectedRating, rating, 0.01);
        verify(mockDistribution, times(0)).calculate(anyDouble(), anyDouble());
    }

    @Test
    public void testBoundaryValues() {
        RatingParameters params = new RatingParameters(1.0, 1.0, 0.0, 5.0, 2);
        RatingCalculatorImpl calculator = new RatingCalculatorImpl(mockDistribution, params);

        double rating = calculator.calculate(0, Integer.MAX_VALUE, 1.0);
        assertEquals(params.lowerBoundary(), rating, 0.0);

        rating = calculator.calculate(10, 10, 0.0);
        assertEquals(0.0, rating, 0.0);

        rating = calculator.calculate(10000, 5000, 1000000.0);
        assertTrue(rating >= params.lowerBoundary());
        assertTrue(rating <= params.upperBoundary());

        int likes = Integer.MAX_VALUE;
        int dislikes = 0;
        int totalVotes = likes + dislikes;
        double totalPopulation = 1;
        double averageNorm = getAverageNorm(likes, dislikes, totalVotes, params);
        double sigma = calculateSigma(totalVotes, totalPopulation);
        when(mockDistribution.calculate(averageNorm, sigma)).thenReturn(1.0);
        rating = calculator.calculate(likes, dislikes, totalPopulation);
        assertEquals(params.upperBoundary(), rating, 0.0);
    }

    @Test
    public void testCalculateThreadSafety() {
        double mockDistributionResult = 0.5;
        NormalDistributionCalculator myMockDistribution = (normalizedValue, sigma) -> mockDistributionResult;
        RatingParameters params = new RatingParameters(1.0, 1.0, 0.0, 5.0, 2);
        RatingCalculator calculator = new RatingCalculatorImpl(myMockDistribution, params);
        int numThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        Map<Integer, Future<Double>> futures = new ConcurrentHashMap<>();
        Map<Integer, Double> results = new ConcurrentHashMap<>();
        for (Integer i = 0; i < numThreads; i++) {
            int likes = ThreadLocalRandom.current().nextInt(1000);
            int dislikes = ThreadLocalRandom.current().nextInt(1000);
            double totalPopulation = 2000;
            double expectedRating = calculateNormalDistributionRating(mockDistributionResult, params);

            results.put(i, expectedRating);
            futures.put(i, executor.submit(() -> calculator.calculate(likes, dislikes, totalPopulation)));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (var futureEntry : futures.entrySet()) {
            try {
                double rating = futureEntry.getValue().get();
                assertEquals(results.get(futureEntry.getKey()), rating, 0.01);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private double getAverageNorm(double likes, double dislikes, double totalVotes, RatingParameters parameters) {
        double likeNorm = likes / totalVotes;
        double dislikeNorm = dislikes / totalVotes;
        return likeNorm * parameters.likeCoef() - dislikeNorm * parameters.dislikeCoef() + 0.5;
    }

    private double calculateSigma(int totalVotes, double totalPopulation) {
        double thirdOfPopulation = totalPopulation / 3.0;
        if (totalVotes <= thirdOfPopulation) {
            return 1;
        } else if (totalVotes <= 2 * thirdOfPopulation) {
            return 1 - (totalVotes - thirdOfPopulation) / thirdOfPopulation;
        } else {
            return 0.5;
        }
    }

    private double calculateNormalDistributionRating(double mockDistributionResult, RatingParameters params) {
        return (params.upperBoundary() - params.lowerBoundary()) * mockDistributionResult + params.lowerBoundary();
    }

    private double calculateExponentialRating(int dislikes, double totalPopulation, RatingParameters params) {
        return (params.upperBoundary() - params.lowerBoundary())
                * Math.exp(-dislikes / totalPopulation) / 2.0;
    }
}
