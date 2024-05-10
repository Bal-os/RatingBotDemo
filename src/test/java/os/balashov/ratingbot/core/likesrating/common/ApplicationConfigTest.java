package os.balashov.ratingbot.core.likesrating.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import os.balashov.ratingbot.core.likesrating.events.EventsConfiguration;
import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveRatingEvent;
import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveVoteEvent;
import os.balashov.ratingbot.core.likesrating.rating.ports.usecases.SavePostRating;
import os.balashov.ratingbot.core.likesrating.votes.ports.usecases.SaveUserVote;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;
import os.balashov.ratingbot.core.likesrating.rating.ports.dtos.PostRating;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationConfigTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private EventsConfiguration applicationConfig;

    @Test
    public void testSaveUserVote() {
        SaveUserVote saveUserVote = applicationConfig.saveUserVote(applicationEventPublisher);
        saveUserVote.save(1, 1L, 1L, Marks.LIKE);
        verify(applicationEventPublisher, times(1)).publishEvent(any(SaveVoteEvent.class));
    }

    @Test
    public void testSavePostRating() {
        SavePostRating savePostRating = applicationConfig.savePostRating(applicationEventPublisher);
        savePostRating.save(1, 1L, PostRating.create(10, 5, 7.5));
        verify(applicationEventPublisher, times(1)).publishEvent(any(SaveRatingEvent.class));
    }
}
