package os.balashov.ratingbot.integration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import os.balashov.ratingbot.infrastructure.sql.common.entities.RatingEntity;
import os.balashov.ratingbot.utils.TestDataGenerator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@ActiveProfiles({"local", "dev"})
public class DatabaseLoadTest {
    private static final List<String> queries = List.of(
            "SELECT u.userId, COUNT(c) FROM UserEntity u JOIN u.chat c GROUP BY u.userId",
            "SELECT r.id.chatId, AVG(r.rating) FROM RatingEntity r GROUP BY r.id.chatId",
            "SELECT m.chat.chatId, COUNT(m) FROM Message m GROUP BY m.chat.chatId",
            "SELECT m.userEntity.userId, COUNT(m) FROM Message m GROUP BY m.userEntity.userId ORDER BY COUNT(m) DESC",
            "SELECT m.chat.chatId, COUNT(m) FROM Message m GROUP BY m.chat.chatId ORDER BY COUNT(m) DESC",
            "SELECT o.id, COUNT(e) FROM Organizers o JOIN o.events e GROUP BY o.id",
            "SELECT l.id, COUNT(u) FROM Location l JOIN l.userEntities u GROUP BY l.id",
            "SELECT r.id.messageId, r.likes, r.dislikes FROM RatingEntity r",
            "SELECT v.userEntity.userId, COUNT(v) FROM UserVote v GROUP BY v.userEntity.userId",
            "SELECT v.userEntity.userId, COUNT(v) FROM UserVote v WHERE v.vote = os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks.LIKE GROUP BY v.userEntity.userId"
    );

    @Autowired
    private TestDataGenerator testDataGenerator;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testDatabaseLoad() throws InterruptedException {
        int testAmount = 50;
        testDataGenerator.generateTestData(testAmount);

        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < testAmount; i++) {
            executorService.submit(() -> {
                for (String query : queries) {
                    Query q = entityManager.createQuery(query);
                    var result = q.getResultList();
                    System.out.println(result);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}