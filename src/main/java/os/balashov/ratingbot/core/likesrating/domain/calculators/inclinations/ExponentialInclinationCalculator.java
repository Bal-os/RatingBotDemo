package os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations;

public final class ExponentialInclinationCalculator implements InclinationCalculator {
    public double calculate(int inclinationFactor) {
        return 0.1 * Math.exp(inclinationFactor);
    }
}