package os.balashov.ratingbot.core.likesrating.events.ports.services;

import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveVoteEvent;

import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;

public interface VoteEventsQueueService {
    ConcurrentMap<Long, SaveVoteEvent> removeVoteEventsQueue(int messageId, long chatId);

    ConcurrentMap<Long, SaveVoteEvent> addVoteEventToQueue(int messageId, long chatId,
                                                           SaveVoteEvent event, BiFunction<SaveVoteEvent, SaveVoteEvent, SaveVoteEvent> computeFunction);
}