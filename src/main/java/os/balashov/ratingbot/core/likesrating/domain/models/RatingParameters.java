package os.balashov.ratingbot.core.likesrating.domain.models;

public record RatingParameters(int inclinationFactor,
                               int lowerBound,
                               int upperBound,
                               int precision) {
}
