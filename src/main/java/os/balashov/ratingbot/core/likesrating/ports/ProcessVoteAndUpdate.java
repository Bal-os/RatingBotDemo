package os.balashov.ratingbot.core.likesrating.ports;

import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;

public interface ProcessVoteAndUpdate {
    double processVoteAndUpdate(int messageId, long chatId, long userId, Marks vote);
}
