package os.balashov.ratingbot.core.likesrating.calculate.ports.entity;

public record RatingParameters(int inclinationFactor,
                               int lowerBound,
                               int upperBound,
                               int precision) {
}
