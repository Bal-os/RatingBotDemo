package os.balashov.ratingbot.core.likesrating.calculate.strategies;

import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ports.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.ports.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.ports.entity.RatingParameters;
import os.balashov.ratingbot.core.likesrating.calculate.ports.RatingStrategy;

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

    public double calculateRating(int likes, int dislikes, int totalUsers) {
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

