package os.balashov.ratingbot.infrastructure.sql.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import os.balashov.ratingbot.infrastructure.sql.common.entities.LocationEntity;

public interface SqlLocationRepository extends JpaRepository<LocationEntity, Long> {
}
