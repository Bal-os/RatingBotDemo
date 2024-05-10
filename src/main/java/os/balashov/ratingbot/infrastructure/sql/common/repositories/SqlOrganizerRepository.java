package os.balashov.ratingbot.infrastructure.sql.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import os.balashov.ratingbot.infrastructure.sql.common.entities.OrganizersEntity;

public interface SqlOrganizerRepository extends JpaRepository<OrganizersEntity, Long> {
}
