package os.balashov.ratingbot.core.likesrating.domain.calculators.inclinations;

@FunctionalInterface
public interface InclinationCalculator {
    double calculate(int factor);
}