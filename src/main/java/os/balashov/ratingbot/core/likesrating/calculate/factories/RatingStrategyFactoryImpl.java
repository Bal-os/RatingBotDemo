package os.balashov.ratingbot.core.likesrating.calculate.factories;

import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ports.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.ports.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.ports.RatingStrategy;
import os.balashov.ratingbot.core.likesrating.calculate.ports.entity.RatingParameters;
import os.balashov.ratingbot.core.likesrating.calculate.ports.RatingStrategyFactory;
import os.balashov.ratingbot.core.likesrating.calculate.strategies.InclinedParameterizedRatingStrategy;

public class RatingStrategyFactoryImpl implements RatingStrategyFactory {
    public RatingStrategy createInclinedParameterizedRatingStrategy(InclinedRatingCalculator inclinedRatingCalculator,
                                                                    InclinationCalculator inclinationCalculator,
                                                                    RatingParameters parameters) {
        return new InclinedParameterizedRatingStrategy(inclinedRatingCalculator, inclinationCalculator, parameters);
    }
}
