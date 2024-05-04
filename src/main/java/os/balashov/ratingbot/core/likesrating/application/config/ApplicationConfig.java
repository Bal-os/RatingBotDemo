package os.balashov.ratingbot.core.likesrating.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import os.balashov.ratingbot.core.likesrating.application.repositories.GetRatingRepository;
import os.balashov.ratingbot.core.likesrating.application.repositories.SaveRatingRepository;
import os.balashov.ratingbot.core.likesrating.application.repositories.SaveVoteRepository;
import os.balashov.ratingbot.core.likesrating.application.usecases.ChangePostRating;
import os.balashov.ratingbot.core.likesrating.application.usecases.FindPostRating;
import os.balashov.ratingbot.core.likesrating.application.usecases.SavePostRating;
import os.balashov.ratingbot.core.likesrating.application.usecases.SaveUserVote;
import os.balashov.ratingbot.core.likesrating.ports.CreatePostRating;


@Configuration
@EnableAspectJAutoProxy
public class ApplicationConfig {
    @Bean
    SaveUserVote addUserVote(SaveVoteRepository saveVoteRepository) {
        return saveVoteRepository::saveUserVote;
    }

    @Bean
    SavePostRating savePostRating(SaveRatingRepository saveRatingRepository) {
        return saveRatingRepository::saveRating;
    }

    @Bean
    FindPostRating findPostRating(GetRatingRepository getRatingRepository) {
        return getRatingRepository::getRating;
    }

    @Bean
    ChangePostRating changePostRating() {
        return new ChangePostRating() {
        };
    }

    @Bean
    CreatePostRating createPostRating() {
        return new CreatePostRating() {
        };
    }
}
