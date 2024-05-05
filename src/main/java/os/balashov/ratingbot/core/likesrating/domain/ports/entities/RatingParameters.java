package os.balashov.ratingbot.core.likesrating.domain.ports.entities;

public record RatingParameters(int inclinationFactor,
                               int lowerBound,
                               int upperBound,
                               int precision) {
}
