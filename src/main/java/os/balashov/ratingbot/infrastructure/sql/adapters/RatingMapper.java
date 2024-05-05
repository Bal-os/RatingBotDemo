package os.balashov.ratingbot.infrastructure.sql.adapters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import os.balashov.ratingbot.core.likesrating.ports.CreatePostRating;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;
import os.balashov.ratingbot.infrastructure.sql.entities.MessageKey;
import os.balashov.ratingbot.infrastructure.sql.entities.RatingEntity;

@Component
@AllArgsConstructor
public class RatingMapper {
    private final CreatePostRating creator;

    public PostRating fromRatingEntity(RatingEntity ratingEntity) {
        return creator.create(ratingEntity.getLikes(), ratingEntity.getDislikes(), ratingEntity.getRating());
    }

    public RatingEntity toRatingEntity(MessageKey key, PostRating postRating) {
        return RatingEntity.builder()
                .id(key)
                .likes(postRating.likes())
                .dislikes(postRating.dislikes())
                .rating(postRating.rating())
                .build();
    }
}
