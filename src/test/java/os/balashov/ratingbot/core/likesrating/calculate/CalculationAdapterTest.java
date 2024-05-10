package os.balashov.ratingbot.core.likesrating.calculate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ports.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.TangentsInclinationCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.ports.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.TangentsInclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.factories.RatingStrategyFactoryImpl;
import os.balashov.ratingbot.core.likesrating.calculate.ports.RatingStrategy;
import os.balashov.ratingbot.core.likesrating.calculate.ports.entity.RatingParameters;
import os.balashov.ratingbot.core.likesrating.calculate.adapters.CalculationAdapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CalculationAdapterTest {

    @Test
    public void testCalculateRating() {
        RatingStrategy strategy = Mockito.mock(RatingStrategy.class);
        when(strategy.calculateRating(100, 50, 200)).thenReturn(5.0);

        CalculationAdapter service = new CalculationAdapter(strategy);
        double result = service.calculateRating(100, 50, 200);

        assertEquals(5.0, result, 0.0);
        Mockito.verify(strategy).calculateRating(100, 50, 200);
    }

    @Test
    public void integrationTest() {
        RatingStrategyFactoryImpl factory = new RatingStrategyFactoryImpl();
        InclinedRatingCalculator inclinationRatingCalculator = new TangentsInclinedRatingCalculator();
        InclinationCalculator inclinationCalculator = new TangentsInclinationCalculator();
        RatingParameters parameters = new RatingParameters(0, 1, 100, 2);
        RatingStrategy strategy = factory.createInclinedParameterizedRatingStrategy(inclinationRatingCalculator, inclinationCalculator, parameters);

        CalculationAdapter service = new CalculationAdapter(strategy);
        double result = service.calculateRating(50, 50, 200);

        assertEquals(50.0, result, 1.5);
    }
}
