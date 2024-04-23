package os.balashov.ratingbot.core.likesrating.domain.factories;

import os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.ratings.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.domain.models.RatingParameters;
import os.balashov.ratingbot.core.likesrating.domain.strategies.InclinedParameterizedRatingStrategy;
import os.balashov.ratingbot.core.likesrating.domain.strategies.RatingStrategy;

public class RatingStrategyFactoryImpl implements RatingStrategyFactory {
    public RatingStrategy createInclinedParameterizedRatingStrategy(InclinedRatingCalculator inclinedRatingCalculator,
                                                                    InclinationCalculator inclinationCalculator,
                                                                    RatingParameters parameters) {
        return new InclinedParameterizedRatingStrategy(inclinedRatingCalculator, inclinationCalculator, parameters);
    }
}
