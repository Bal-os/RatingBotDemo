package os.balashov.ratingbot.infrastructure.sql.common.adapters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import os.balashov.ratingbot.core.likesrating.rating.ports.dtos.PostRating;
import os.balashov.ratingbot.core.likesrating.rating.ports.repositories.GetRatingRepository;
import os.balashov.ratingbot.core.likesrating.votes.ports.repositories.GetVoteRepository;
import os.balashov.ratingbot.core.likesrating.rating.ports.usecases.CreatePostRating;
import os.balashov.ratingbot.infrastructure.sql.common.entities.MessageKey;
import os.balashov.ratingbot.infrastructure.sql.common.entities.RatingEntity;
import os.balashov.ratingbot.infrastructure.sql.common.entities.UserVote;
import os.balashov.ratingbot.infrastructure.sql.common.repositories.SqlRatingRepository;
import os.balashov.ratingbot.infrastructure.sql.common.repositories.SqlVoteRepository;

import java.util.Optional;
import java.util.function.Function;

@Configuration
public class AdaptersConfiguration {
    @Bean
    public GetVoteRepository getVoteRepository(SqlVoteRepository sqlVoteRepository) {
        return (messageId, chatId, userId) -> sqlVoteRepository.findByMessageChatAndUserIds(messageId, chatId, userId)
                .map(UserVote::getVote);
    }

    @Bean
    public GetRatingRepository getRatingRepository(SqlRatingRepository repository, CreatePostRating creator) {
        return (messageId, chatId) -> Optional.of(new MessageKey(messageId, chatId))
                .flatMap(repository::findById)
                .map(mapper(creator));
    }

    private Function<RatingEntity, PostRating> mapper(CreatePostRating creator) {
        return ratingEntity -> creator.create(ratingEntity.getLikes(),
                ratingEntity.getDislikes(),
                ratingEntity.getRating());
    }
}
