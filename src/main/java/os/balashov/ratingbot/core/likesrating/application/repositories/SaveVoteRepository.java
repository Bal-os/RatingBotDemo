package os.balashov.ratingbot.core.likesrating.application.repositories;

import os.balashov.ratingbot.core.common.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;

public interface SaveVoteRepository {
    @Loggable(message = "Repository: Try to save user {3} vote {4} for message {1} in chat {2} to database")
    void saveUserVote(int messageId, long chatId, long userId, Marks vote);
}
