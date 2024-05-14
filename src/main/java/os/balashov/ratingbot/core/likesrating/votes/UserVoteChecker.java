package os.balashov.ratingbot.core.likesrating.votes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;
import os.balashov.ratingbot.core.likesrating.votes.ports.repositories.GetVoteRepository;
import os.balashov.ratingbot.core.likesrating.votes.ports.usecases.CheckUserVote;

@Service
@AllArgsConstructor
public class UserVoteChecker implements CheckUserVote {
    private final GetVoteRepository getVoteRepository;

    @Override
    public boolean isUserVoted(int messageId, long chatId, long userId) {
        return getVoteRepository.getUserVote(messageId, chatId, userId).isPresent();
    }

    @Override
    public boolean isVoteNotChanged(int messageId, long chatId, long userId, Marks vote) {
        return getVoteRepository.getUserVote(messageId, chatId, userId)
                .map(v -> v == vote)
                .orElse(false);
    }
}
