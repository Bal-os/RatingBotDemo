package os.balashov.ratingbot.infrastructure.sql;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import os.balashov.ratingbot.core.likesrating.application.services.PostRatingService;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;
import os.balashov.ratingbot.infrastructure.sql.adapters.RatingMapper;
import os.balashov.ratingbot.infrastructure.sql.adapters.SaveRatingAdapter;
import os.balashov.ratingbot.infrastructure.sql.entities.MessageKey;
import os.balashov.ratingbot.infrastructure.sql.entities.RatingEntity;
import os.balashov.ratingbot.infrastructure.sql.entities.UserEntity;
import os.balashov.ratingbot.infrastructure.sql.repositories.SqlRatingRepository;
import os.balashov.ratingbot.infrastructure.sql.repositories.SqlUserRepository;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaveRatingAdapterTest {
    private final RatingMapper realRatingMapper = new RatingMapper(new PostRatingService());
    @Mock
    private SqlRatingRepository sqlRatingRepository;
    @Mock
    private SqlUserRepository sqlUserRepository;
    @Mock
    private RatingMapper ratingMapper;
    @InjectMocks
    private SaveRatingAdapter saveRatingAdapter;


    @Test
    public void testSaveRating_ExistingUserAndRating() {
        int messageId = 10;
        long chatId = 123L;
        PostRating rating = PostRating.create(10, 5, 7.5);
        long userId = 456L;
        List<Marks> votes = Collections.singletonList(Marks.LIKE);
        MessageKey key = new MessageKey(messageId, chatId);

        UserEntity userMock = mock(UserEntity.class);
        var optionalMock = mock(Optional.class);
        RatingEntity expectedRatingEntity = realRatingMapper.toRatingEntity(key, rating);
        when(sqlUserRepository.findById(userId)).thenReturn(optionalMock);
        when(optionalMock.orElse(any())).thenReturn(userMock);
        when(sqlRatingRepository.findById(key)).thenReturn(Optional.of(expectedRatingEntity));
        when(userMock.getVotes()).thenReturn(new LinkedHashSet<>());
        when(ratingMapper.toRatingEntity(key, rating)).thenReturn(expectedRatingEntity);

        saveRatingAdapter.saveRating(key, rating, userId, votes);

        verify(sqlUserRepository).findById(userId);
        verify(sqlRatingRepository).findById(key);


        verify(optionalMock).orElse(any());
        verify(sqlUserRepository).save(userMock);
    }

    @Test
    public void testSaveRating_NewUser() {
        int messageId = 10;
        long chatId = 123L;
        PostRating rating = PostRating.create(10, 5, 7.5);
        long userId = 456L;
        List<Marks> votes = Collections.singletonList(Marks.LIKE);
        MessageKey key = new MessageKey(messageId, chatId);

        UserEntity userMock = mock(UserEntity.class);
        var optionalMock = mock(Optional.class);
        when(sqlUserRepository.findById(userId)).thenReturn(optionalMock);
        when(optionalMock.orElse(any())).thenReturn(userMock);
        when(sqlRatingRepository.findById(key)).thenReturn(Optional.of(RatingEntity.builder().id(key).build()));
        when(userMock.getVotes()).thenReturn(new LinkedHashSet<>());

        saveRatingAdapter.saveRating(key, rating, userId, votes);

        verify(sqlUserRepository, times(1)).findById(userId);
        verify(sqlUserRepository, times(1)).save(userMock);
    }

    @Test
    public void testSaveRating_NewRating() {
        int messageId = 10;
        long chatId = 123L;
        PostRating rating = PostRating.create(10, 5, 7.5);
        long userId = 456L;
        List<Marks> votes = Collections.singletonList(Marks.LIKE);
        MessageKey key = new MessageKey(messageId, chatId);

        UserEntity expectedUser = UserEntity.builder()
                .userId(userId)
                .votes(new LinkedHashSet<>())
                .build();
        when(sqlUserRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        when(sqlRatingRepository.findById(key)).thenReturn(Optional.empty());
        when(ratingMapper.toRatingEntity(key, rating)).thenReturn(RatingEntity.builder().id(key).likes(10).dislikes(5).rating(7.5).build());

        saveRatingAdapter.saveRating(key, rating, userId, votes);

        verify(sqlUserRepository).save(expectedUser);
    }
}

