package os.balashov.ratingbot.core.likesrating.likesrating.services;

import os.balashov.ratingbot.core.likesrating.likesrating.ports.CalculateRating;
import os.balashov.ratingbot.core.likesrating.likesrating.ports.RatingStrategy;

public class CalculationService implements CalculateRating {
    private final RatingStrategy ratingStrategy;

    public CalculationService(RatingStrategy ratingStrategy) {
        this.ratingStrategy = ratingStrategy;
    }

    public double calculateRating(int likes, int dislikes, int totalUsers) {

        return ratingStrategy.calculateRating(likes, dislikes, totalUsers);
    }
}
