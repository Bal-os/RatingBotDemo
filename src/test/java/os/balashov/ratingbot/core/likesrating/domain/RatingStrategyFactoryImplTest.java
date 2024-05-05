package os.balashov.ratingbot.core.likesrating.domain;

import org.junit.jupiter.api.Test;
import os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations.ExponentialInclinationCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.ratings.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.ratings.TangentsInclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.domain.ports.RatingStrategyFactory;
import os.balashov.ratingbot.core.likesrating.domain.factories.RatingStrategyFactoryImpl;
import os.balashov.ratingbot.core.likesrating.domain.ports.entities.RatingParameters;
import os.balashov.ratingbot.core.likesrating.domain.strategies.InclinedParameterizedRatingStrategy;
import os.balashov.ratingbot.core.likesrating.domain.ports.RatingStrategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RatingStrategyFactoryImplTest {

    @Test
    public void testCreateInclinedParameterizedRatingStrategy() {
        InclinationCalculator inclinationCalculator = new ExponentialInclinationCalculator();
        InclinedRatingCalculator ratingCalculator = new TangentsInclinedRatingCalculator();
        RatingParameters parameters = new RatingParameters(1, 0, 10, 2);

        RatingStrategyFactory factory = new RatingStrategyFactoryImpl();
        RatingStrategy strategy = factory.createInclinedParameterizedRatingStrategy(ratingCalculator, inclinationCalculator, parameters);

        assertTrue(strategy instanceof InclinedParameterizedRatingStrategy);
    }
}
