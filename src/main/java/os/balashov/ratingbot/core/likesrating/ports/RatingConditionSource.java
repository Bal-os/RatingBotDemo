package os.balashov.ratingbot.core.likesrating.ports;

import os.balashov.ratingbot.core.likesrating.application.dtos.RatingCondition;

public interface RatingConditionSource {
    RatingCondition getRatingCondition();
}
