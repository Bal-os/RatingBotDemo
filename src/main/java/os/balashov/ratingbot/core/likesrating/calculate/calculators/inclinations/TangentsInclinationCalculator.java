package os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations;

import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ports.InclinationCalculator;

public class TangentsInclinationCalculator implements InclinationCalculator {
    public double calculate(int factor) {
        double base = 0.75 + Math.tanh(-1 * factor) * 0.25;
        double distribution = (1 + Math.tanh(factor)) / 2;
        double sign = Math.signum(factor);

        return base + distribution * sign;
    }
}
