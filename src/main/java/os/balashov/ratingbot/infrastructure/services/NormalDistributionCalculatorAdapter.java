package os.balashov.ratingbot.infrastructure.services;

import org.apache.commons.math3.distribution.NormalDistribution;
import os.balashov.ratingbot.core.likesrating.ports.NormalDistributionCalculator;

public class NormalDistributionCalculatorAdapter implements NormalDistributionCalculator {
    @Override
    public double calculate(Double normalizedValue, Double sigma) {
        NormalDistribution normalDistribution = new NormalDistribution(normalizedValue, sigma);
        return normalDistribution.cumulativeProbability(normalizedValue);
    }
}
