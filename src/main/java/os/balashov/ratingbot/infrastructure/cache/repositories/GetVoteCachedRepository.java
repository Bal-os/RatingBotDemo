package os.balashov.ratingbot.infrastructure.cache.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;
import os.balashov.ratingbot.core.likesrating.votes.ports.repositories.GetVoteRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Primary
@Service
public class GetVoteCachedRepository implements GetVoteRepository {
    public final AtomicInteger requestCount;
    private final GetVoteRepository getVoteRepository;

    public GetVoteCachedRepository(@Qualifier("getVoteRepository") GetVoteRepository getVoteRepository) {
        this.getVoteRepository = getVoteRepository;
        requestCount = new AtomicInteger(1);
    }

    @Cacheable(value = "votes", condition = "#root.target.requestCount.getAndIncrement() % 2 == 0")
    public Optional<Marks> getUserVote(int messageId, long chatId, long userId) {
        return getVoteRepository.getUserVote(messageId, chatId, userId);
    }
}
