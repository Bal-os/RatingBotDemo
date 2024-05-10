package os.balashov.ratingbot.infrastructure.sql.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import os.balashov.ratingbot.infrastructure.sql.common.entities.UserEntity;

public interface SqlUserRepository extends JpaRepository<UserEntity, Long> {
    default void upsert(UserEntity userEntity) {
        save(userEntity);
    }
}
