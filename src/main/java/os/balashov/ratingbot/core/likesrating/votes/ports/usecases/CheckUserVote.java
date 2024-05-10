package os.balashov.ratingbot.core.likesrating.votes.ports.usecases;

import os.balashov.ratingbot.core.common.application.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;

public interface CheckUserVote {
    @Loggable(message = "Service: Try to check if user {3} have vote for message {1} in chat {2}")
    boolean isUserVoted(int messageId, long chatId, long userId);

    @Loggable(message = "Service: Try to check if user {3} vote {4} for message {1} in chat {2} is not changed")
    boolean isVoteNotChanged(int messageId, long chatId, long userId, Marks vote);
}
