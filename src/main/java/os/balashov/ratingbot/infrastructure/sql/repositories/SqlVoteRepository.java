package os.balashov.ratingbot.infrastructure.sql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import os.balashov.ratingbot.infrastructure.sql.entities.UserVote;

import java.util.Optional;

public interface SqlVoteRepository extends JpaRepository<UserVote, Long> {
    @Query("SELECT vote " +
            "FROM UserVote vote " +
            "WHERE vote.userEntity.userId = :userId " +
            "AND vote.ratingEntity.id.chatId = :chatId " +
            "AND vote.ratingEntity.id.messageId = :messageId")
    Optional<UserVote> findByUserIdAndMessageKey(
            @Param("messageId") Integer messageId,
            @Param("chatId") Long chatId,
            @Param("userId") Long userId
    );
}
