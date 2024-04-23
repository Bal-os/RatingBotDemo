package os.balashov.ratingbot.core.likesrating.domain.strategies;

import os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.ratings.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.domain.models.RatingParameters;

public final class InclinedParameterizedRatingStrategy implements RatingStrategy {
    private final RatingParameters parameters;
    private final InclinationCalculator inclinationCalculator;
    private final InclinedRatingCalculator inclinedRatingCalculator;

    public InclinedParameterizedRatingStrategy(InclinedRatingCalculator inclinedRatingCalculator,
                                               InclinationCalculator inclinationCalculator,
                                               RatingParameters parameters) {
        this.parameters = parameters;
        this.inclinationCalculator = inclinationCalculator;
        this.inclinedRatingCalculator = inclinedRatingCalculator;
    }

    public double calculateRating(int totalUsers, int likes, int dislikes) {
        double inclination = inclinationCalculator.calculate(parameters.inclinationFactor());
        double baseValue = inclinedRatingCalculator.calculate(likes, dislikes, totalUsers, inclination);
        return parametrizeValue(baseValue);
    }

    private double parametrizeValue(double value) {
        double rawValue = (parameters.upperBound() - parameters.lowerBound()) * value + parameters.lowerBound();
        double multiplier = Math.pow(10, parameters.precision());
        double roundedValue = Math.round(rawValue * multiplier) / multiplier;
        return Math.min(parameters.upperBound(), Math.max(parameters.lowerBound(), roundedValue));
    }
}

