package os.balashov.ratingbot.core.likesrating.rating;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import os.balashov.ratingbot.core.likesrating.rating.ports.repositories.GetRatingRepository;
import os.balashov.ratingbot.core.likesrating.rating.ports.usecases.FindPostRating;


@Configuration
public class RatingConfig {
    @Bean
    public FindPostRating findPostRating(GetRatingRepository getRatingRepository) {
        return getRatingRepository::getRating;
    }
}
