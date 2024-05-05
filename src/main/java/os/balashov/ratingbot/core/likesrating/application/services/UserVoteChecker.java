package os.balashov.ratingbot.core.likesrating.application.services;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.likesrating.application.repositories.GetVoteRepository;
import os.balashov.ratingbot.core.likesrating.ports.CheckUserVote;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserVoteChecker implements CheckUserVote {
    private final GetVoteRepository getVoteRepository;

    @Override
    public boolean isUserVoted(int messageId, long chatId, long userId) {
        return getUserVote(messageId, chatId, userId).isPresent();
    }

    @Override
    public boolean isVoteNotChanged(int messageId, long chatId, long userId, Marks vote) {
        return getUserVote(messageId, chatId, userId)
                .map(v -> v == vote)
                .orElse(false);
    }

    @Caching
    protected Optional<Marks> getUserVote(int messageId, long chatId, long userId) {
        return getVoteRepository.getUserVote(messageId, chatId, userId);
    }
}
