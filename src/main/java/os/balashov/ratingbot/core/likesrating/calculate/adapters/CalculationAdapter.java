package os.balashov.ratingbot.core.likesrating.calculate.adapters;

import os.balashov.ratingbot.core.likesrating.rating.ports.usecases.CalculateRating;
import os.balashov.ratingbot.core.likesrating.calculate.ports.RatingStrategy;

public class CalculationAdapter implements CalculateRating {
    private final RatingStrategy ratingStrategy;

    public CalculationAdapter(RatingStrategy ratingStrategy) {
        this.ratingStrategy = ratingStrategy;
    }

    public double calculateRating(int likes, int dislikes, int totalUsers) {

        return ratingStrategy.calculateRating(likes, dislikes, totalUsers);
    }
}
