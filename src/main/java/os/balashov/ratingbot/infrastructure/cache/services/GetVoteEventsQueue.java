package os.balashov.ratingbot.infrastructure.cache.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveVoteEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class GetVoteEventsQueue {
    @Cacheable(value = "voteEventsQueue")
    public ConcurrentMap<Long, SaveVoteEvent> getVoteEventsQueue(int messageId, long chatId) {
        return new ConcurrentHashMap<>();
    }
}
