package os.balashov.ratingbot.infrastructure.sql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import os.balashov.ratingbot.infrastructure.sql.entities.UserEntity;

public interface SqlUserRepository extends JpaRepository<UserEntity, Long> {
}
