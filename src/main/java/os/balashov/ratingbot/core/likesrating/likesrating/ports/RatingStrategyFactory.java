package os.balashov.ratingbot.core.likesrating.likesrating.ports;

import os.balashov.ratingbot.core.likesrating.likesrating.calculators.inclinations.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.likesrating.calculators.ratings.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.likesrating.ports.entities.RatingParameters;

public interface RatingStrategyFactory {
    RatingStrategy createInclinedParameterizedRatingStrategy(InclinedRatingCalculator inclinedRatingCalculator,
                                                             InclinationCalculator inclinationCalculator,
                                                             RatingParameters parameters);
}
