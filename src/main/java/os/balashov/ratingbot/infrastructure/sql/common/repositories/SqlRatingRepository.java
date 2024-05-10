package os.balashov.ratingbot.infrastructure.sql.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import os.balashov.ratingbot.infrastructure.sql.common.entities.MessageKey;
import os.balashov.ratingbot.infrastructure.sql.common.entities.RatingEntity;

public interface SqlRatingRepository extends JpaRepository<RatingEntity, MessageKey> {
    default void upsert(RatingEntity ratingEntity) {
        save(ratingEntity);
    }
}
