package os.balashov.ratingbot.core.likesrating.domain.models.values;

public record RatingParameters(double likeCoef,
                               double dislikeCoef,
                               double lowerBoundary,
                               double upperBoundary,
                               int precision) {
}
