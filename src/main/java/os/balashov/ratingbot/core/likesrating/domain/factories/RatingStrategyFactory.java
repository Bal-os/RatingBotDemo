package os.balashov.ratingbot.core.likesrating.domain.factories;

import os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.ratings.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.ratings.RatingCalculator;
import os.balashov.ratingbot.core.likesrating.domain.models.RatingParameters;
import os.balashov.ratingbot.core.likesrating.domain.strategies.RatingStrategy;

public interface RatingStrategyFactory {
    RatingStrategy createInclinedParameterizedRatingStrategy(InclinedRatingCalculator inclinedRatingCalculator,
                                                             InclinationCalculator inclinationCalculator,
                                                             RatingParameters parameters);

    default RatingStrategy createSimpleRatingStrategy(RatingCalculator ratingCalculator) {
        return (totalUsers, likes, dislikes) -> ratingCalculator.calculate(likes, dislikes, totalUsers);
    }
}
