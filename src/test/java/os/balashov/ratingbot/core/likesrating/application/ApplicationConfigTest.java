package os.balashov.ratingbot.core.likesrating.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import os.balashov.ratingbot.core.likesrating.application.config.ApplicationConfig;
import os.balashov.ratingbot.core.likesrating.application.events.SaveRatingEvent;
import os.balashov.ratingbot.core.likesrating.application.events.SaveVoteEvent;
import os.balashov.ratingbot.core.likesrating.application.usecases.SavePostRating;
import os.balashov.ratingbot.core.likesrating.application.usecases.SaveUserVote;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationConfigTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private ApplicationConfig applicationConfig;

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
