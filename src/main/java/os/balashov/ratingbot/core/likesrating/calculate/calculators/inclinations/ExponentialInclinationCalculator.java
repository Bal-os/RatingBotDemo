package os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations;

import os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ports.InclinationCalculator;

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