package os.balashov.ratingbot.core.likesrating.application.repositories;

import os.balashov.ratingbot.core.common.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;

import java.util.Optional;

public interface GetVoteRepository {
    @Loggable(message = "Repository: Try to get user vote for message {1} from chat {2} from database")
    Optional<Marks> getUserVote(int messageId, long chatId, long userId);
}
