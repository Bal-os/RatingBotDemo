package os.balashov.ratingbot.core.likesrating.calculate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import os.balashov.ratingbot.core.likesrating.calculate.adapters.CalculationAdapter;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ExponentialInclinationCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ports.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.TangentsInclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.calculators.ratings.ports.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.calculate.factories.RatingStrategyFactoryImpl;
import os.balashov.ratingbot.core.likesrating.calculate.ports.RatingStrategy;
import os.balashov.ratingbot.core.likesrating.calculate.ports.entity.RatingParameters;

@Configuration
public class CalculateConfiguration {
    @Bean
    public RatingParameters ratingParameters(@Value("${likesrating.inclination-factor}") int inclinationFactor,
                                             @Value("${likesrating.lower-bound}") int lowerBound,
                                             @Value("${likesrating.upper-bound}") int upperBound,
                                             @Value("${likesrating.precision}") int precision) {
        return new RatingParameters(inclinationFactor, lowerBound, upperBound, precision);
    }

    @Bean
    public CalculationAdapter ratingCalculationService(RatingParameters ratingParameters) {
        return new CalculationAdapter(ratingStrategy(ratingParameters));
    }

    private RatingStrategy ratingStrategy(RatingParameters ratingParameters) {
        return new RatingStrategyFactoryImpl().createInclinedParameterizedRatingStrategy(
                inclinedRatingCalculator(),
                inclinationCalculator(),
                ratingParameters);
    }

    private InclinationCalculator inclinationCalculator() {
        return new ExponentialInclinationCalculator();
    }

    private InclinedRatingCalculator inclinedRatingCalculator() {
        return new TangentsInclinedRatingCalculator();
    }
}
