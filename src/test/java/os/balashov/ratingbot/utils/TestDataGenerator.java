package os.balashov.ratingbot.utils;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;
import os.balashov.ratingbot.infrastructure.sql.common.entities.*;
import os.balashov.ratingbot.infrastructure.sql.common.repositories.*;
import os.balashov.ratingbot.infrastructure.sql.entities.*;
import os.balashov.ratingbot.infrastructure.sql.repositories.*;

import java.time.LocalDateTime;
import java.util.*;

@Component
//@ActiveProfiles({"dev", "qa"})
public class TestDataGenerator {
    private final Random random = new Random();
    private final SqlChatRepository chatRepository;
    private final SqlUserRepository userRepository;
    private final SqlMessageRepository messageRepository;
    private final SqlRatingRepository ratingRepository;
    private final SqlVoteRepository userVoteRepository;
    private final SqlLocationRepository locationRepository;
    private final SqlOrganizerRepository organizerRepository;
    private final SqlEventRepository eventRepository;

    @Autowired
    public TestDataGenerator(SqlChatRepository chatRepository, SqlUserRepository userRepository, SqlMessageRepository messageRepository, SqlRatingRepository ratingRepository, SqlVoteRepository userVoteRepository, SqlLocationRepository locationRepository, SqlOrganizerRepository organizerRepository, SqlEventRepository eventRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.ratingRepository = ratingRepository;
        this.userVoteRepository = userVoteRepository;
        this.locationRepository = locationRepository;
        this.organizerRepository = organizerRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public void generateTestData(int max) {
        if (max < 2) {
            throw new IllegalArgumentException("Max value should be greater than 1");
        }
        initialSave();

        for (int i = 2; i <= max; i++) {
            for(int j = 0; j < max; j++) {
                int index = 10 * j + i;
                // Create and save a user
                var user = new UserEntity();
                user.setUserId((long) index);
                user.setChats(new HashSet<>(getRandomEntities(ChatEntity.class, chatRepository, i)));
                user.setMessages(new ArrayList<>(getRandomEntities(MessageEntity.class, messageRepository, i)));
                user.setVotes(new ArrayList<>(getRandomEntities(UserVote.class, userVoteRepository, i)));
                userRepository.save(user);

                // Create and save a chat
                var chat = new ChatEntity();
                chat.setChatId((long) index);
                chat.setName("Test Chat " + index);
                var users = getRandomEntities(UserEntity.class, userRepository, i);
                var members = new HashSet<>(users);
                chat.setMembers(members);
                members.forEach(u -> u.getChats().add(chat));
                chatRepository.save(chat);

                // Create and save a message
                var messageKey = new MessageKey();
                messageKey.setMessageId(index);
                messageKey.setChatId(chat.getChatId());
                MessageEntity messageEntity = new MessageEntity();
                messageEntity.setId(messageKey);
                messageEntity.setChat(chat);
                user = getRandomEntityFromList(users).orElse(user);
                messageEntity.setUser(user);
                user.getMessages().add(messageEntity);
                messageEntity.setText("Test Message " + index);
                messageRepository.save(messageEntity);
            }
            MessageKey messageKey = new MessageKey(i, (long) i);
            ChatEntity chatEntity = chatRepository.getReferenceById(messageKey.getChatId());
            UserEntity user = userRepository.getReferenceById((long) i);

            // Create and save a user vote
            UserVote userVote = new UserVote();
            userVote.setId((long) i);
            userVote.setUser(user);
            userVote.setVote(Marks.LIKE);
            user.getVotes().add(userVote);
            userVoteRepository.save(userVote);

            // Create and save a rating
            RatingEntity rating = new RatingEntity();
            rating.setId(messageKey);
            rating.setLikes(10);
            rating.setDislikes(0);
            rating.setRating(5.0);
            rating.setVotes(List.of(userVote));
            ratingRepository.save(rating);

            // Create and save a location
            LocationEntity locationEntity = new LocationEntity();
            locationEntity.setId((long) i);
            locationEntity.setName("Test Location " + i);
            locationEntity.setAddress("Test Address " + i);
            locationEntity.setNumber("Test Number " + i);
            locationEntity.setGoogleMapsLink("Test Google Maps Link " + i);
            locationEntity.setType("Test Type " + i);
            locationRepository.save(locationEntity);

            // Create and save an organizer
            OrganizersEntity organizer = new OrganizersEntity();
            organizer.setId((long) i);
            organizer.setName("Test Organizer " + i);
            organizer.setInstagramLink("Test Instagram Link " + i);
            var orgChat = getRandomEntity(ChatEntity.class, chatRepository, i).orElse(chatEntity);
            organizer.setChannel(orgChat);
            organizer.setEvents(new HashSet<>(getRandomEntities(EventEntity.class, eventRepository, i)));
            organizer.setUsers(getRandomEntities(orgChat.getMembers(), i));
            organizerRepository.save(organizer);

            // Create and save an event
            EventEntity eventEntity = new EventEntity();
            RatingEntity ratingEntity = getRandomEntity(RatingEntity.class, ratingRepository, i).orElse(rating);
            eventEntity.setId((long) i);
            eventEntity.setDate(LocalDateTime.now().plusDays(random.nextInt(max)));
            eventEntity.setRating(ratingEntity);
            var org = getRandomEntities(OrganizersEntity.class, organizerRepository, i);
            var loc = getRandomEntity(LocationEntity.class, locationRepository, i).orElse(locationEntity);
            eventEntity.setOrganizers(new HashSet<>(org));
            eventEntity.setLocation(loc);
            eventRepository.save(eventEntity);
        }
    }

    private void initialSave() {
        UserVote userVote = new UserVote();
        userVote.setVote(Marks.LIKE);
        userVoteRepository.save(userVote);

        UserEntity user = new UserEntity();
        user.setUserId((long) 1);
        user.setVotes(new ArrayList<>(List.of(userVote)));
        user.setMessages(new ArrayList<>());
        user.setChats(new HashSet<>());
        userRepository.save(user);

        ChatEntity chat = new ChatEntity();
        chat.setChatId((long) 1);
        chat.setName("Test Chat " + 1);
        user.setChats(new HashSet<>(List.of(chat)));
        List<UserEntity> users = List.of(user);
        Set<UserEntity> members = new HashSet<>(users);
        chat.setMembers(members);
        chatRepository.save(chat);

        MessageKey messageKey = new MessageKey();
        messageKey.setMessageId(1);
        messageKey.setChatId(chat.getChatId());
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(messageKey);
        messageEntity.setChat(chat);
        messageEntity.setUser(user);
        messageEntity.setText("Test Message " + 1);
        user.setMessages(new ArrayList<>(List.of(messageEntity)));
        messageRepository.save(messageEntity);

        RatingEntity rating = new RatingEntity();
        rating.setId(messageKey);
        rating.setVotes(new ArrayList<>(List.of(userVote)));
        rating.setLikes(10);
        rating.setDislikes(0);
        rating.setRating(5.0);
        ratingRepository.save(rating);

        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setName("Test Location " + 1);
        locationEntity.setAddress("Test Address " + 1);
        locationEntity.setNumber("Test Number " + 1);
        locationEntity.setGoogleMapsLink("Test Google Maps Link " + 1);
        locationEntity.setType("Test Type " + 1);
        locationEntity = locationRepository.save(locationEntity);

        OrganizersEntity organizer = new OrganizersEntity();
        organizer.setName("Test Organizer " + 1);
        organizer.setInstagramLink("Test Instagram Link " + 1);
        organizer.setChannel(chat);
        organizer.setUsers(new LinkedList<>(List.of(user)));
        organizer = organizerRepository.save(organizer);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setName("Test Event " + 1);
        eventEntity.setDescription("Test Description " + 1);
        eventEntity.setDate(LocalDateTime.now());
        eventEntity.setRating(rating);
        eventEntity.setOrganizers(new LinkedHashSet<>(List.of(organizer)));
        organizer.setEvents(new HashSet<>(List.of(eventEntity)));
        eventEntity.setLocation(locationEntity);
        eventRepository.save(eventEntity);
    }

    private <T, ID> List<T> getRandomEntities(Class<T> entityClass, JpaRepository<T, ID> repository, int index) {
        List <T> entities = new LinkedList<>();
        for (int i = 0; i < index; i++) {
            var value = getRandomEntity(entityClass, repository, index);
            value.ifPresent(entities::add);
        }
        return entities;
    }

    private <T> List<T> getRandomEntities(Collection<T> collection, int index) {
        int count = random.nextInt(index - 1) + 1;
        return collection.stream()
                .limit(count)
                .map(u -> getRandomEntityFromList(collection).orElse(u))
                .toList();
    }

    private <T, ID> Optional<T> getRandomEntity(Class<T> entityClass, JpaRepository<T, ID> repository, int index) {
        int count = random.nextInt(index - 1) + 1;
        if (entityClass == MessageEntity.class || entityClass == RatingEntity.class) {
            MessageKey messageKey = new MessageKey();
            messageKey.setMessageId(count);
            messageKey.setChatId((long) count);
            return Optional.of(repository.getReferenceById((ID) messageKey));
        }
        return Optional.of(repository.getReferenceById((ID) Long.valueOf(Math.min(count, index))));
    }


    private <T> Optional<T> getRandomEntityFromList(Collection<T> entities) {
        int size = entities.size();
        if (size == 0) {
            return Optional.empty();
        }
        int index = random.nextInt(size);
        return entities.stream()
                .skip(index)
                .findFirst();
    }
}
