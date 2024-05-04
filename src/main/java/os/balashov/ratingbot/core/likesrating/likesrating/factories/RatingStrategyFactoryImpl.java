package os.balashov.ratingbot.core.likesrating.likesrating.factories;

import os.balashov.ratingbot.core.likesrating.likesrating.calculators.inclinations.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.likesrating.calculators.ratings.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.likesrating.ports.RatingStrategy;
import os.balashov.ratingbot.core.likesrating.likesrating.ports.RatingStrategyFactory;
import os.balashov.ratingbot.core.likesrating.likesrating.ports.entities.RatingParameters;
import os.balashov.ratingbot.core.likesrating.likesrating.strategies.InclinedParameterizedRatingStrategy;

public class RatingStrategyFactoryImpl implements RatingStrategyFactory {
    public RatingStrategy createInclinedParameterizedRatingStrategy(InclinedRatingCalculator inclinedRatingCalculator,
                                                                    InclinationCalculator inclinationCalculator,
                                                                    RatingParameters parameters) {
        return new InclinedParameterizedRatingStrategy(inclinedRatingCalculator, inclinationCalculator, parameters);
    }
}
