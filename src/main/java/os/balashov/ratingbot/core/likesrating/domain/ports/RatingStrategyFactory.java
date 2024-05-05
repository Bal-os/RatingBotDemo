package os.balashov.ratingbot.core.likesrating.domain.ports;

import os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.ratings.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.domain.ports.entities.RatingParameters;

public interface RatingStrategyFactory {
    RatingStrategy createInclinedParameterizedRatingStrategy(InclinedRatingCalculator inclinedRatingCalculator,
                                                             InclinationCalculator inclinationCalculator,
                                                             RatingParameters parameters);
}
