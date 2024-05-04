package os.balashov.ratingbot.core.likesrating.likesrating.calculators.inclinations;

public class TangentsInclinationCalculator implements InclinationCalculator {
    public double calculate(int factor) {
        double base = 0.75 + Math.tanh(-1 * factor) * 0.25;
        double distribution = (1 + Math.tanh(factor)) / 2;
        double sign = Math.signum(factor);

        return base + distribution * sign;
    }
}
