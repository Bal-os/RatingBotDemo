package os.balashov.ratingbot.core.likesrating.process.ports;

import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;

public interface ProcessVoteAndUpdate {
    double processVoteAndUpdate(int messageId, long chatId, long userId, Marks vote);
}
