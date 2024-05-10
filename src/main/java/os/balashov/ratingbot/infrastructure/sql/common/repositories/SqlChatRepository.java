package os.balashov.ratingbot.infrastructure.sql.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import os.balashov.ratingbot.infrastructure.sql.common.entities.ChatEntity;

public interface SqlChatRepository extends JpaRepository<ChatEntity, Long> {
    default void upsert(ChatEntity chatEntity) {
        save(chatEntity);
    }
}
