package os.balashov.ratingbot.core.likesrating.application.services;

import os.balashov.ratingbot.core.likesrating.application.dtos.RatingCondition;
import os.balashov.ratingbot.core.likesrating.domain.strategies.RatingStrategy;
import os.balashov.ratingbot.core.likesrating.ports.RatingConditionSource;

public class RatingService {
    private final RatingStrategy ratingStrategy;
    private final RatingConditionSource ratingConditionSource;

    public RatingService(RatingStrategy ratingStrategy, RatingConditionSource ratingConditionSource) {
        this.ratingStrategy = ratingStrategy;
        this.ratingConditionSource = ratingConditionSource;
    }

    public double calculateRating() {
        RatingCondition ratingCondition = ratingConditionSource.getRatingCondition();
        return ratingStrategy.calculateRating(ratingCondition.totalUsers(),
                ratingCondition.likes(), ratingCondition.dislikes());
    }
}
