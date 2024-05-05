package os.balashov.ratingbot.core.likesrating.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations.TangentsInclinationCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.ratings.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.ratings.TangentsInclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.domain.factories.RatingStrategyFactoryImpl;
import os.balashov.ratingbot.core.likesrating.domain.ports.RatingStrategy;
import os.balashov.ratingbot.core.likesrating.domain.ports.entities.RatingParameters;
import os.balashov.ratingbot.core.likesrating.domain.services.CalculationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CalculationServiceTest {

    @Test
    public void testCalculateRating() {
        RatingStrategy strategy = Mockito.mock(RatingStrategy.class);
        when(strategy.calculateRating(100, 50, 200)).thenReturn(5.0);

        CalculationService service = new CalculationService(strategy);
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

        CalculationService service = new CalculationService(strategy);
        double result = service.calculateRating(0, 2, 2);

        assertEquals(50.0, result, 1.5);
    }
}
