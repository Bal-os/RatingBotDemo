package os.balashov.ratingbot.infrastructure.sql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import os.balashov.ratingbot.core.likesrating.application.events.SaveRatingEvent;
import os.balashov.ratingbot.core.likesrating.application.events.SaveVoteEvent;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;
import os.balashov.ratingbot.infrastructure.sql.adapters.RatingSaver;
import os.balashov.ratingbot.infrastructure.sql.adapters.RatingVoteListener;
import os.balashov.ratingbot.infrastructure.sql.entities.MessageKey;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingVoteListenerTest {

    @Mock
    private ConcurrentMap<MessageKey, PostRating> latestRatingMap;

    @Mock
    private ConcurrentMap<MessageKey, List<SaveVoteEvent>> voteEventsMap;

    @Mock
    private RatingSaver ratingSaver;

    @InjectMocks
    private RatingVoteListener listener;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        Field ratingMap = RatingVoteListener.class.getDeclaredField("latestRating");
        ratingMap.setAccessible(true);
        ratingMap.set(listener, latestRatingMap);
        Field voteEventsMap = RatingVoteListener.class.getDeclaredField("voteEvents");
        voteEventsMap.setAccessible(true);
        voteEventsMap.set(listener, voteEventsMap);
        Method saveVotesWithIfExists = RatingVoteListener.class.getDeclaredMethod("saveVotesWithIfExists", MessageKey.class, PostRating.class);
        saveVotesWithIfExists.setAccessible(true);
        Method saveData = RatingVoteListener.class.getDeclaredMethod("saveData", MessageKey.class, PostRating.class, List.class);
        saveData.setAccessible(true);
    }

    @Test
    public void handleSaveRatingEventTest() {
        PostRating rating = PostRating.create(1, 2, 3.0);
        SaveRatingEvent mockSaveRating = mock(SaveRatingEvent.class);
        when(mockSaveRating.getPostRating()).thenReturn(rating);
        when(mockSaveRating.getMessageId()).thenReturn(1);
        when(mockSaveRating.getChatId()).thenReturn(2L);
        when(latestRatingMap.put(any(MessageKey.class), rating)).thenCallRealMethod();
        when(voteEventsMap.remove(any(MessageKey.class))).thenReturn(Collections.emptyList());

        listener.handleSaveRatingEvent(mockSaveRating);

        verify(latestRatingMap).put(any(MessageKey.class), eq(rating));
        verify(voteEventsMap).remove(any(MessageKey.class));
    }

    @Test
    public void handleSaveVoteEventTest() {
        SaveVoteEvent saveVoteEvent = new SaveVoteEvent(this, 1, 2L, 3L, Marks.LIKE);
        when(voteEventsMap.computeIfAbsent(any(MessageKey.class), any())).thenReturn(new LinkedList<>());
        when(latestRatingMap.computeIfPresent(any(MessageKey.class), any())).thenReturn(PostRating.create(1, 2, 3.0));

        listener.handleSaveVoteEvent(saveVoteEvent);

        verify(voteEventsMap).computeIfAbsent(any(MessageKey.class), any());
        verify(latestRatingMap).computeIfPresent(any(MessageKey.class), any());
    }

    @Test
    public void saveVotesWithIfExistsTest() throws Exception {
        PostRating rating = PostRating.create(1, 2, 3.0);
        List<SaveVoteEvent> voteEventsList = Collections.singletonList(new SaveVoteEvent(this, 1, 2L, 3L, Marks.LIKE));
        MessageKey key = new MessageKey(1, 2L);
        when(voteEventsMap.remove(eq(key))).thenReturn(voteEventsList);
        doNothing().when(ratingSaver).saveRating(eq(key), eq(rating), any(), eq(voteEventsList.stream().map(SaveVoteEvent::getVote).toList()));

        Method method = RatingVoteListener.class.getDeclaredMethod("saveVotesWithIfExists", MessageKey.class, PostRating.class);
        method.invoke(listener, key, rating);

        verify(voteEventsMap).remove(eq(key));
        verify(ratingSaver).saveRating(eq(key), eq(rating), eq(3L), eq(Collections.singletonList(Marks.LIKE)));
    }

}
