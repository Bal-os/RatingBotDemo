package os.balashov.ratingbot.core.likesrating.common;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import os.balashov.ratingbot.core.likesrating.process.VoteAndUpdateListener;
import os.balashov.ratingbot.core.likesrating.rating.ports.usecases.ChangePostRating;
import os.balashov.ratingbot.core.likesrating.rating.ports.usecases.FindPostRating;
import os.balashov.ratingbot.core.likesrating.rating.ports.usecases.SavePostRating;
import os.balashov.ratingbot.core.likesrating.votes.ports.usecases.SaveUserVote;
import os.balashov.ratingbot.core.likesrating.rating.ports.usecases.CalculateRating;
import os.balashov.ratingbot.core.common.ports.ChatMemberCounter;
import os.balashov.ratingbot.core.likesrating.votes.ports.usecases.CheckUserVote;
import os.balashov.ratingbot.core.likesrating.rating.ports.usecases.CreatePostRating;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;
import os.balashov.ratingbot.core.likesrating.rating.ports.dtos.PostRating;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VoteAndUpdateListenerTest {
    @Mock
    private CheckUserVote checkUserVote;
    @Mock
    private CalculateRating calculateRatingService;
    @Mock
    private SaveUserVote saveUserVote;
    @Mock
    private ChatMemberCounter chatMemberCounter;
    @Mock
    private FindPostRating findPostRating;
    @Mock
    private SavePostRating savePostRating;
    @Mock
    private ChangePostRating changePostRating;
    @Mock
    private CreatePostRating createPostRating;
    @InjectMocks
    private VoteAndUpdateListener voteAndUpdateListener;

    @Test
    public void testProcessVoteAndUpdate() {
        PostRating postRating = PostRating.create(10, 5, 7.5);
        when(findPostRating.find(1, 1L)).thenReturn(Optional.of(postRating));
        when(checkUserVote.isUserVoted(1, 1L, 1L)).thenReturn(false);
        when(changePostRating.incrementRatingCounts(Marks.LIKE, postRating))
                .thenReturn(PostRating.create(11, 5, 7.5));
        when(chatMemberCounter.countChatMembers(1L)).thenReturn(100);
        when(calculateRatingService.calculateRating(11, 5, 100)).thenReturn(7.6);
        when(changePostRating.setNewRating(PostRating.create(11, 5, 7.5), 7.6))
                .thenReturn(PostRating.create(11, 5, 7.6));
        double result = voteAndUpdateListener.processVoteAndUpdate(1, 1L, 1L, Marks.LIKE);

        assertEquals(7.6, result, 0.0);
        verify(findPostRating).find(1, 1L);
        verify(checkUserVote).isUserVoted(1, 1L, 1L);
        verify(changePostRating).incrementRatingCounts(Marks.LIKE, postRating);
        verify(chatMemberCounter).countChatMembers(1L);
        verify(calculateRatingService).calculateRating(11, 5, 100);
        verify(saveUserVote).save(1, 1L, 1L, Marks.LIKE);
        verify(changePostRating).setNewRating(any(PostRating.class), eq(7.6));
        verify(savePostRating).save(anyInt(), anyLong(), any());
    }

    @Test
    public void testProcessVoteAndUpdateWithDislike() {
        PostRating postRating = PostRating.create(10, 5, 7.5);
        when(findPostRating.find(1, 1L)).thenReturn(Optional.of(postRating));
        when(checkUserVote.isUserVoted(1, 1L, 1L)).thenReturn(false);
        when(changePostRating.incrementRatingCounts(Marks.DISLIKE, postRating))
                .thenReturn(PostRating.create(10, 6, 7.5));
        when(chatMemberCounter.countChatMembers(1L)).thenReturn(100);
        when(calculateRatingService.calculateRating(10, 6, 100)).thenReturn(7.4);
        when(changePostRating.setNewRating(PostRating.create(10, 6, 7.5), 7.4))
                .thenReturn(PostRating.create(10, 6, 7.4));

        double result = voteAndUpdateListener.processVoteAndUpdate(1, 1L, 1L, Marks.DISLIKE);

        assertEquals(7.4, result, 0.0);
        verify(findPostRating).find(1, 1L);
        verify(checkUserVote).isUserVoted(1, 1L, 1L);
        verify(changePostRating).incrementRatingCounts(Marks.DISLIKE, postRating);
        verify(chatMemberCounter).countChatMembers(1L);
        verify(calculateRatingService).calculateRating(10, 6, 100);
        verify(saveUserVote).save(1, 1L, 1L, Marks.DISLIKE);
        verify(changePostRating).setNewRating(any(PostRating.class), eq(7.4));
        verify(savePostRating).save(anyInt(), anyLong(), any());
    }

    @Test
    public void testProcessVoteAndUpdateWithNewPostRating() {
        PostRating postRating = PostRating.create(0, 0, 0);
        when(findPostRating.find(1, 1L)).thenReturn(Optional.empty());
        when(createPostRating.createEmpty()).thenReturn(postRating);
        when(changePostRating.incrementRatingCounts(Marks.LIKE, postRating))
                .thenReturn(PostRating.create(1, 0, 0));
        when(chatMemberCounter.countChatMembers(1L)).thenReturn(100);
        when(calculateRatingService.calculateRating(1, 0, 100)).thenReturn(3.5);
        when(changePostRating.setNewRating(PostRating.create(1, 0, 0), 3.5))
                .thenReturn(PostRating.create(1, 0, 3.5));

        double result = voteAndUpdateListener.processVoteAndUpdate(1, 1L, 1L, Marks.LIKE);

        assertEquals(3.5, result, 0.0);

        verify(findPostRating).find(1, 1L);
        verify(createPostRating).createEmpty();
        verify(changePostRating).incrementRatingCounts(eq(Marks.LIKE), any(PostRating.class));
        verify(chatMemberCounter).countChatMembers(1L);
        verify(calculateRatingService).calculateRating(1, 0, 100);
        verify(saveUserVote).save(1, 1L, 1L, Marks.LIKE);
        verify(changePostRating).setNewRating(any(PostRating.class), eq(3.5));
        verify(savePostRating).save(anyInt(), anyLong(), any());
    }

    @Test
    public void testProcessVoteAndUpdateWithExistingUserVote() {
        when(findPostRating.find(1, 1L)).thenReturn(Optional.of(PostRating.create(10, 5, 7.5)));
        when(checkUserVote.isUserVoted(1, 1L, 1L)).thenReturn(true);
        when(checkUserVote.isVoteNotChanged(1, 1L, 1L, Marks.LIKE)).thenReturn(true);

        double result = voteAndUpdateListener.processVoteAndUpdate(1, 1L, 1L, Marks.LIKE);

        assertEquals(7.5, result, 0.0);

        verify(findPostRating).find(1, 1L);
        verify(checkUserVote).isUserVoted(1, 1L, 1L);
        verify(checkUserVote).isVoteNotChanged(1, 1L, 1L, Marks.LIKE);
    }

    @Test
    public void testProcessVoteAndUpdateWithChangedUserVote() {
        PostRating postRatingMock = PostRating.create(10, 5, 7.5);
        when(findPostRating.find(1, 1L)).thenReturn(Optional.of(postRatingMock));
        when(checkUserVote.isUserVoted(1, 1L, 1L)).thenReturn(true);
        when(checkUserVote.isVoteNotChanged(1, 1L, 1L, Marks.LIKE)).thenReturn(false);
        when(changePostRating.swapRatingCounts(Marks.LIKE, postRatingMock)).thenReturn(PostRating.create(11, 4, 7.5));
        when(chatMemberCounter.countChatMembers(1L)).thenReturn(100);
        when(calculateRatingService.calculateRating(11, 4, 100)).thenReturn(8.0);
        when(changePostRating.setNewRating(any(PostRating.class), eq(8.0)))
                .thenReturn(postRatingMock);

        double result = voteAndUpdateListener.processVoteAndUpdate(1, 1L, 1L, Marks.LIKE);

        assertEquals(8.0, result, 0.0);

        verify(findPostRating).find(1, 1L);
        verify(checkUserVote).isUserVoted(1, 1L, 1L);
        verify(checkUserVote).isVoteNotChanged(1, 1L, 1L, Marks.LIKE);
        verify(changePostRating).swapRatingCounts(Marks.LIKE, postRatingMock);
        verify(chatMemberCounter).countChatMembers(1L);
        verify(calculateRatingService).calculateRating(11, 4, 100);
        verify(saveUserVote).save(1, 1L, 1L, Marks.LIKE);
        verify(changePostRating).setNewRating(any(PostRating.class), eq(8.0));
        verify(savePostRating).save(anyInt(), anyLong(), any());
    }

    @Test
    public void testProcessVoteAndUpdateNewRating() {
        int messageId = 10;
        long chatId = 123L;
        long userId = 456L;
        Marks vote = Marks.LIKE;
        PostRating postRating = PostRating.create(0, 0, 0);

        when(findPostRating.find(messageId, chatId)).thenReturn(Optional.of(postRating));
        when(checkUserVote.isUserVoted(messageId, chatId, userId)).thenReturn(false);
        when(changePostRating.incrementRatingCounts(eq(vote), any(PostRating.class)))
                .thenReturn(PostRating.create(1, 0, 0));
        when(chatMemberCounter.countChatMembers(chatId)).thenReturn(100);
        when(calculateRatingService.calculateRating(1, 0, 100)).thenReturn(3.5);
        when(changePostRating.setNewRating(any(PostRating.class), eq(3.5)))
                .thenReturn(PostRating.create(1, 0, 3.5));

        double rating = voteAndUpdateListener.processVoteAndUpdate(messageId, chatId, userId, vote);

        assertEquals(3.5, rating, 0.001);

        verify(findPostRating).find(messageId, chatId);
        verify(changePostRating).incrementRatingCounts(eq(vote), any(PostRating.class));
        verify(chatMemberCounter).countChatMembers(chatId);
        verify(calculateRatingService).calculateRating(1, 0, 100);
        verify(saveUserVote).save(messageId, chatId, userId, vote);
        verify(changePostRating).setNewRating(any(PostRating.class), eq(3.5));
        verify(savePostRating).save(anyInt(), anyLong(), any());
    }
}
