package os.balashov.ratingbot.infrastructure.sql.common.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.likesrating.rating.ports.dtos.PostRating;
import os.balashov.ratingbot.core.likesrating.rating.ports.repositories.SaveRatingRepository;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;
import os.balashov.ratingbot.core.postmenegment.ports.repository.SavePostRepository;
import os.balashov.ratingbot.infrastructure.sql.common.entities.*;
import os.balashov.ratingbot.infrastructure.sql.common.repositories.*;

import java.util.Optional;

@Service()
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class UpsertAdapter implements SaveRatingRepository, SavePostRepository {
    private final SqlRatingRepository ratingRepository;
    private final SqlUserRepository userRepository;
    private final SqlVoteRepository voteRepository;
    private final SqlMessageRepository messageRepository;
    private final SqlChatRepository chatRepository;
    private final SqlEventRepository eventRepository;


    @Override
    public void savePost(int messageId, long chatId, long userId, String text) {
        saveEmptyChat(chatId);
        saveEmptyUser(userId);

        var key = new MessageKey(messageId, chatId);
        saveMessage(key, userId, text);
    }

    @Override
    public void saveRating(int messageId, long chatId, long userId, PostRating rating, Marks vote) {
        saveEmptyUser(userId);
        saveEmptyUser(chatId);
        saveEmptyChat(chatId);

        var key = new MessageKey(messageId, chatId);
        saveMessage(key, chatId, null);
        saveRating(key, rating, null);
        saveVote(key, userId, vote);
    }


    private void saveMessage(MessageKey key, Long userId, String text) {
        var message = MessageEntity.builder()
                .id(key)
                .chat(chatRepository.getReferenceById(key.getChatId()))
                .user(userRepository.getReferenceById(userId));
        Optional.ofNullable(text)
                .ifPresent(message::text);
        messageRepository.upsert(message.build());
    }

    private void saveRating(MessageKey key, PostRating rating, Long eventId) {
        var ratingEntity = RatingEntity.builder()
                .id(key)
                .likes(rating.likes())
                .dislikes(rating.dislikes())
                .rating(rating.rating());
        Optional.ofNullable(eventId)
                .ifPresent(sqlEventId -> ratingEntity.event(eventRepository.getReferenceById(sqlEventId)));
        ratingRepository.upsert(ratingEntity.build());
    }

    private void saveVote(MessageKey key, Long userId, Marks vote) {
        voteRepository.upsert(UserVote.builder()
                .rating(ratingRepository.getReferenceById(key))
                .user(userRepository.getReferenceById(userId))
                .vote(vote)
                .build());
    }

    private void saveEmptyUser(Long userId) {
        userRepository.upsert(UserEntity.builder()
                .userId(userId)
                .build());
    }

    private void saveEmptyChat(Long chatId) {
        chatRepository.upsert(ChatEntity.builder()
                .chatId(chatId)
                .build());
    }

}
