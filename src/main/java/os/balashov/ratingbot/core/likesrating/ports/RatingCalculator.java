package os.balashov.ratingbot.core.likesrating.ports;

@FunctionalInterface
public interface RatingCalculator {
    double calculate(Integer likes, Integer dislikes, Double totalPopulation);
}
