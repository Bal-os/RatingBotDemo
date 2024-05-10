package os.balashov.ratingbot.infrastructure.sql.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import os.balashov.ratingbot.infrastructure.sql.common.entities.MessageEntity;
import os.balashov.ratingbot.infrastructure.sql.common.entities.MessageKey;

public interface SqlMessageRepository extends JpaRepository<MessageEntity, MessageKey> {
    default void upsert(MessageEntity messageEntity) {
        save(messageEntity);
    }
}
