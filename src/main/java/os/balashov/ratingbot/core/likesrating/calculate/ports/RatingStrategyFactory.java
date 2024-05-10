package os.balashov.ratingbot.core.likesrating.calculate.ports;

import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ports.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.ports.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.ports.entity.RatingParameters;

public interface RatingStrategyFactory {
    RatingStrategy createInclinedParameterizedRatingStrategy(InclinedRatingCalculator inclinedRatingCalculator,
                                                             InclinationCalculator inclinationCalculator,
                                                             RatingParameters parameters);
}
