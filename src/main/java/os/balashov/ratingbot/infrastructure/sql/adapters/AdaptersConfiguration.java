package os.balashov.ratingbot.infrastructure.sql.adapters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import os.balashov.ratingbot.core.likesrating.application.repositories.GetRatingRepository;
import os.balashov.ratingbot.core.likesrating.application.repositories.GetVoteRepository;
import os.balashov.ratingbot.infrastructure.sql.entities.MessageKey;
import os.balashov.ratingbot.infrastructure.sql.entities.UserVote;
import os.balashov.ratingbot.infrastructure.sql.repositories.SqlRatingRepository;
import os.balashov.ratingbot.infrastructure.sql.repositories.SqlVoteRepository;

import java.util.Optional;

@Configuration
public class AdaptersConfiguration {
    @Bean
    public GetVoteRepository getVoteRepository(SqlVoteRepository sqlVoteRepository) {
        return (messageId, chatId, userId) -> sqlVoteRepository.findByUserIdAndMessageKey(messageId, chatId, userId)
                .map(UserVote::getVote);
    }

    @Bean
    public GetRatingRepository getRatingRepository(SqlRatingRepository repository, RatingMapper mapper) {
        return (messageId, chatId) -> Optional.of(new MessageKey(messageId, chatId))
                .flatMap(repository::findById)
                .map(mapper::fromRatingEntity);
    }
}
