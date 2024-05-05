package os.balashov.ratingbot.core.likesrating.domain.services;

import os.balashov.ratingbot.core.likesrating.domain.ports.CalculateRating;
import os.balashov.ratingbot.core.likesrating.domain.ports.RatingStrategy;

public class CalculationService implements CalculateRating {
    private final RatingStrategy ratingStrategy;

    public CalculationService(RatingStrategy ratingStrategy) {
        this.ratingStrategy = ratingStrategy;
    }

    public double calculateRating(int likes, int dislikes, int totalUsers) {

        return ratingStrategy.calculateRating(likes, dislikes, totalUsers);
    }
}
