package os.balashov.ratingbot.core.likesrating.application;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import os.balashov.ratingbot.core.likesrating.application.repositories.GetVoteRepository;
import os.balashov.ratingbot.core.likesrating.application.services.UserVoteChecker;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserVoteCheckerTest {
    @Mock
    private GetVoteRepository getVoteRepository;

    @InjectMocks
    private UserVoteChecker userVoteChecker;

    @Test
    public void testIsUserVoted_ExistingVote() {
        int messageId = 10;
        long chatId = 123L;
        long userId = 456L;
        Marks vote = Marks.LIKE;

        when(getVoteRepository.getUserVote(messageId, chatId, userId)).thenReturn(Optional.of(vote));

        boolean hasVoted = userVoteChecker.isUserVoted(messageId, chatId, userId);

        assertTrue(hasVoted);

        verify(getVoteRepository).getUserVote(messageId, chatId, userId);
    }

    @Test
    public void testIsUserVoted_NoVote() {
        int messageId = 10;
        long chatId = 123L;
        long userId = 456L;

        when(getVoteRepository.getUserVote(messageId, chatId, userId)).thenReturn(Optional.empty());

        boolean hasVoted = userVoteChecker.isUserVoted(messageId, chatId, userId);

        assertFalse(hasVoted);

        verify(getVoteRepository).getUserVote(messageId, chatId, userId);
    }

    @Test
    public void testIsVoteNotChanged_SameVote() {
        int messageId = 10;
        long chatId = 123L;
        long userId = 456L;
        Marks vote = Marks.DISLIKE;

        when(getVoteRepository.getUserVote(messageId, chatId, userId)).thenReturn(Optional.of(vote));

        boolean voteNotChanged = userVoteChecker.isVoteNotChanged(messageId, chatId, userId, vote);

        assertTrue(voteNotChanged);

        verify(getVoteRepository).getUserVote(messageId, chatId, userId);
    }

    @Test
    public void testIsVoteNotChanged_DifferentVote() {
        int messageId = 10;
        long chatId = 123L;
        long userId = 456L;
        Marks vote = Marks.LIKE;
        Marks previousVote = Marks.DISLIKE;

        when(getVoteRepository.getUserVote(messageId, chatId, userId)).thenReturn(Optional.of(previousVote));

        boolean voteNotChanged = userVoteChecker.isVoteNotChanged(messageId, chatId, userId, vote);

        assertFalse(voteNotChanged);

        verify(getVoteRepository).getUserVote(messageId, chatId, userId);
    }
}
