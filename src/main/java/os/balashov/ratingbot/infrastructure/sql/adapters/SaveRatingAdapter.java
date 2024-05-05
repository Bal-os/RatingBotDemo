package os.balashov.ratingbot.infrastructure.sql.adapters;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;
import os.balashov.ratingbot.infrastructure.sql.entities.MessageKey;
import os.balashov.ratingbot.infrastructure.sql.entities.RatingEntity;
import os.balashov.ratingbot.infrastructure.sql.entities.UserEntity;
import os.balashov.ratingbot.infrastructure.sql.entities.UserVote;
import os.balashov.ratingbot.infrastructure.sql.repositories.SqlRatingRepository;
import os.balashov.ratingbot.infrastructure.sql.repositories.SqlUserRepository;
import os.balashov.ratingbot.infrastructure.sql.repositories.SqlVoteRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SaveRatingAdapter implements RatingSaver {
    private final SqlRatingRepository sqlRatingRepository;
    private final SqlUserRepository sqlUserRepository;
    private final SqlVoteRepository sqlVoteRepository;
    private final RatingMapper ratingMapper;

    @Override
    @Transactional
    public void saveRating(MessageKey key, PostRating rating, Long userId, List<Marks> votes) {
        var userEntity = sqlUserRepository.findById(userId)
                .orElse(getNewUserEntity(userId));

        var ratingEntity = sqlRatingRepository.findById(key)
                .orElse(ratingMapper.toRatingEntity(key, rating));

        var votesEntity = votes.stream()
                .map(mark -> createUserVote(mark, userEntity, ratingEntity))
                .toList();

        sqlVoteRepository.saveAllAndFlush(votesEntity);
    }

    @NotNull
    private UserVote createUserVote(Marks mark, UserEntity userEntity, RatingEntity ratingEntity) {
        return UserVote.builder()
                .vote(mark)
                .userEntity(userEntity)
                .ratingEntity(ratingEntity)
                .build();
    }

    @NotNull
    private UserEntity getNewUserEntity(Long userId) {
        return UserEntity.builder()
                .userId(userId)
                .votes(new LinkedList<>())
                .build();
    }
}
