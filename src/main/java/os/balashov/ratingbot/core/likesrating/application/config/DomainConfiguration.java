package os.balashov.ratingbot.core.likesrating.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations.ExponentialInclinationCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations.InclinationCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.ratings.InclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.domain.calculators.ratings.TangentsInclinedRatingCalculator;
import os.balashov.ratingbot.core.likesrating.domain.factories.RatingStrategyFactoryImpl;
import os.balashov.ratingbot.core.likesrating.domain.ports.RatingStrategy;
import os.balashov.ratingbot.core.likesrating.domain.ports.entities.RatingParameters;
import os.balashov.ratingbot.core.likesrating.domain.services.CalculationService;

@Configuration
public class DomainConfiguration {
    @Bean
    public RatingParameters ratingParameters(@Value("${likesrating.inclination-factor}") int inclinationFactor,
                                             @Value("${likesrating.lower-bound}") int lowerBound,
                                             @Value("${likesrating.upper-bound}") int upperBound,
                                             @Value("${likesrating.precision}") int precision) {
        return new RatingParameters(inclinationFactor, lowerBound, upperBound, precision);
    }

    @Bean
    public CalculationService ratingCalculationService(RatingParameters ratingParameters) {
        return new CalculationService(ratingStrategy(ratingParameters));
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
