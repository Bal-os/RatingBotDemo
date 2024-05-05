package os.balashov.ratingbot.infrastructure.sql.adapters;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.common.logging.Loggable;
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

@Service
@RequiredArgsConstructor
public class SaveRatingAdapter implements RatingSaver {
    private final SqlRatingRepository sqlRatingRepository;
    private final SqlUserRepository sqlUserRepository;
    private final SqlVoteRepository sqlVoteRepository;
    private final RatingMapper ratingMapper;

    @Override
    @Transactional
    public void saveRating(MessageKey key, PostRating rating, Long userId, Marks vote) {
        RatingEntity ratingEntity = findOrCreateRatingEntityWithNewRating(key, rating);
        UserVote votesEntity = sqlVoteRepository.findByUserIdAndMessageKey(key.getMessageId(), key.getChatId(), userId)
                .map(userVote -> updateUserVote(userVote, ratingEntity, vote))
                .orElseGet(() -> createUserVote(vote, findOrCreateUser(userId), ratingEntity));
        sqlVoteRepository.save(votesEntity);
    }

    @Loggable(message = "SqlAdapter: find or create user with id {1}")
    private UserEntity findOrCreateUser(Long userId) {
        return sqlUserRepository.findById(userId).orElseGet(() -> createUserEntity(userId));
    }

    @Loggable(message = "SqlAdapter: find or create rating entity with key {1} and rating {2}")
    private RatingEntity findOrCreateRatingEntityWithNewRating(MessageKey key, PostRating rating) {
        return sqlRatingRepository.findById(key)
                .map(ratingEntity -> updateRating(ratingEntity, rating))
                .orElseGet(() -> ratingMapper.toRatingEntity(key, rating));
    }

    @Loggable(message = "SqlAdapter: update rating entity {1} with rating {2}")
    private RatingEntity updateRating(RatingEntity ratingEntity, PostRating rating) {
        ratingEntity.setDislikes(rating.dislikes());
        ratingEntity.setRating(rating.rating());
        ratingEntity.setLikes(rating.likes());
        return ratingEntity;
    }

    @Loggable(message = "SqlAdapter: update user vote {1} with rating entity {2} and vote {3}")
    private UserVote updateUserVote(UserVote userVote, RatingEntity ratingEntity, Marks vote) {
        userVote.setRatingEntity(ratingEntity);
        userVote.setVote(vote);
        return userVote;
    }

    @Loggable(message = "SqlAdapter: create user vote with vote {1}, user {2} and rating {3}")
    private UserVote createUserVote(Marks mark, UserEntity userEntity, RatingEntity ratingEntity) {
        return UserVote.builder()
                .vote(mark)
                .userEntity(userEntity)
                .ratingEntity(ratingEntity)
                .build();
    }

    @Loggable(message = "SqlAdapter: create new user with id {1}")
    private UserEntity createUserEntity(Long userId) {
        return UserEntity.builder()
                .userId(userId)
                .votes(new LinkedList<>())
                .build();
    }
}

