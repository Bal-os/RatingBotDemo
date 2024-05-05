package os.balashov.ratingbot.infrastructure.sql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import os.balashov.ratingbot.infrastructure.sql.entities.MessageKey;
import os.balashov.ratingbot.infrastructure.sql.entities.RatingEntity;

public interface SqlRatingRepository extends JpaRepository<RatingEntity, MessageKey> {
}
