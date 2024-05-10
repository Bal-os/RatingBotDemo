package os.balashov.ratingbot.core.likesrating.calculate;

import org.junit.jupiter.api.Test;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ExponentialInclinationCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ports.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.ports.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.TangentsInclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.factories.RatingStrategyFactoryImpl;
import os.balashov.ratingbot.core.likesrating.calculate.ports.RatingStrategy;
import os.balashov.ratingbot.core.likesrating.calculate.ports.RatingStrategyFactory;
import os.balashov.ratingbot.core.likesrating.calculate.ports.entity.RatingParameters;
import os.balashov.ratingbot.core.likesrating.calculate.strategies.InclinedParameterizedRatingStrategy;

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
