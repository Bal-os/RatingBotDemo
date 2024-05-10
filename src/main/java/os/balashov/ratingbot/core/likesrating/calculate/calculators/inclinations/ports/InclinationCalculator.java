package os.balashov.ratingbot.core.likesrating.calculate.calculators.inclinations.ports;

@FunctionalInterface
public interface InclinationCalculator {
    double calculate(int factor);
}