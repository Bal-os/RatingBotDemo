package os.balashov.ratingbot.infrastructure.sql.common.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import os.balashov.ratingbot.infrastructure.sql.common.entities.MessageKey;
import os.balashov.ratingbot.infrastructure.sql.common.entities.UserVote;

import java.util.Optional;

public interface SqlVoteRepository extends JpaRepository<UserVote, Long> {
    @Query("SELECT vote " +
            "FROM users_posts_votes vote " +
            "WHERE vote.rating.id.messageId = :messageId " +
            "AND vote.rating.id.chatId = :chatId " +
            "AND vote.user.userId = :userId")
    Optional<UserVote> findByMessageChatAndUserIds(
            @Param("messageId") Integer messageId,
            @Param("chatId") Long chatId,
            @Param("userId") Long userId
    );

    default Optional<UserVote> findByMessageKeyAndUser(MessageKey key, Long userId) {
        return findByMessageChatAndUserIds(key.getMessageId(), key.getChatId(), userId);
    }

    @Transactional
    default void upsert(UserVote vote) {
        var existingVote = findByMessageKeyAndUser(vote.getRating().getId(), vote.getUser().getUserId());
        if (existingVote.isPresent()) {
            existingVote.get().setVote(vote.getVote());
            saveAndFlush(existingVote.get());
        } else {
            saveAndFlush(vote);
        }
    }
}
