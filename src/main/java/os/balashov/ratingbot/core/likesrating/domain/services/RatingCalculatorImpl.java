package os.balashov.ratingbot.core.likesrating.domain.services;

import os.balashov.ratingbot.core.likesrating.domain.models.values.RatingParameters;
import os.balashov.ratingbot.core.likesrating.ports.NormalDistributionCalculator;
import os.balashov.ratingbot.core.likesrating.ports.RatingCalculator;

public final class RatingCalculatorImpl implements RatingCalculator {
    private final NormalDistributionCalculator distribution;
    private final RatingParameters parameters;

    public RatingCalculatorImpl(NormalDistributionCalculator distribution, RatingParameters parameters) {
        this.distribution = distribution;
        this.parameters = parameters;
    }

    public double calculate(Integer likes, Integer dislikes, Double totalPopulation) {
        int totalVotes = likes + dislikes;

        if (totalVotes <= 0) return 0;

        double rating;
        if (likes > 0) {
            double averageNorm = getAverageNorm(likes, dislikes, totalVotes);
            double sigma = calculateSigma(totalVotes, totalPopulation);

            rating = getDistributedRating(averageNorm, sigma);
        } else {
            rating = getExpDislikesRating(dislikes, totalPopulation);
        }

        return checkBoundary(rating);
    }

    private double getAverageNorm(double likes, double dislikes, double totalVotes) {
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

    private double getDistributedRating(double averageNorm, double sigma) {
        return (parameters.upperBoundary() - parameters.lowerBoundary())
                * distribution.calculate(averageNorm, sigma)
                + parameters.lowerBoundary();
    }

    private double getExpDislikesRating(int dislikes, double totalPopulation) {
        return (parameters.upperBoundary() - parameters.lowerBoundary())
                * Math.exp(-dislikes / totalPopulation) / 2.0;
    }

    private double checkBoundary(double rating) {
        double multiplier = Math.pow(10, parameters.precision());
        double roundedRating = Math.round(rating * multiplier) / multiplier;
        if (roundedRating < parameters.lowerBoundary()) {
            return parameters.lowerBoundary();
        }
        return Math.min(roundedRating, parameters.upperBoundary());
    }
}
