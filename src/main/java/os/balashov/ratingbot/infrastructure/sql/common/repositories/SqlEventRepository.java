package os.balashov.ratingbot.infrastructure.sql.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import os.balashov.ratingbot.infrastructure.sql.common.entities.EventEntity;

public interface SqlEventRepository extends JpaRepository<EventEntity, Long> {
}
