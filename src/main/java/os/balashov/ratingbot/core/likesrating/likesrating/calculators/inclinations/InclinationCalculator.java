package os.balashov.ratingbot.core.likesrating.likesrating.calculators.inclinations;

@FunctionalInterface
public interface InclinationCalculator {
    double calculate(int factor);
}