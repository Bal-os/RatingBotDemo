package os.balashov.ratingbot.core.likesrating.votes.ports.repositories;

import os.balashov.ratingbot.core.common.application.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;

import java.util.Optional;

public interface GetVoteRepository {
    @Loggable(message = "Repository: Try to get user vote for message {1} from chat {2} from database")
    Optional<Marks> getUserVote(int messageId, long chatId, long userId);
}
