package os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations;

public final class ExponentialInclinationCalculator implements InclinationCalculator {
    public double calculate(int factor) {
        double b = 0.25;
        double k = 0.1;

        double base = 0.75;
        double distribution = b / (1 + Math.exp(-k * factor));
        double sign = Math.signum(factor);

        return base + distribution + (0.5 + distribution * 2) * sign;
    }

}