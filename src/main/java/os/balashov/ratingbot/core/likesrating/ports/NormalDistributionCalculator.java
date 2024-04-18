package os.balashov.ratingbot.core.likesrating.ports;

@FunctionalInterface
public interface NormalDistributionCalculator {
    double calculate(Double normalizedValue, Double sigma);
}
