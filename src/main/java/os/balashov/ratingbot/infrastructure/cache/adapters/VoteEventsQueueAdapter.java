package os.balashov.ratingbot.infrastructure.cache.adapters;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveVoteEvent;
import os.balashov.ratingbot.core.likesrating.events.ports.services.VoteEventsQueueService;
import os.balashov.ratingbot.infrastructure.cache.services.GetVoteEventsQueue;

import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
@Service
@AllArgsConstructor
public class VoteEventsQueueAdapter implements VoteEventsQueueService {
    private final GetVoteEventsQueue provider;

    @CachePut(value = "voteEventsQueue")
    public ConcurrentMap<Long, SaveVoteEvent> addVoteEventToQueue(int messageId,
                                                                  long chatId,
                                                                  SaveVoteEvent event,
                                                                  BiFunction<SaveVoteEvent, SaveVoteEvent, SaveVoteEvent> computeFunction) {
        ConcurrentMap<Long, SaveVoteEvent> voteEvents = provider.getVoteEventsQueue(messageId, chatId);
        voteEvents.compute(event.getUserId(), (userId, oldEvent) -> computeFunction.apply(oldEvent, event));
        return voteEvents;
    }

    @CacheEvict(value = "voteEventsQueue")
    public ConcurrentMap<Long, SaveVoteEvent>  removeVoteEventsQueue(int messageId, long chatId) {
        return provider.getVoteEventsQueue(messageId, chatId);
    }
}
