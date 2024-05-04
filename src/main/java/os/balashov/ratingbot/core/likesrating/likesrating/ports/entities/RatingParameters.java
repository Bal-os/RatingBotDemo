package os.balashov.ratingbot.core.likesrating.likesrating.ports.entities;

public record RatingParameters(int inclinationFactor,
                               int lowerBound,
                               int upperBound,
                               int precision) {
}
