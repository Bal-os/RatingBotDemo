package os.balashov.ratingbot.core.postmenegment.ports.repository;

import jakarta.transaction.Transactional;
import os.balashov.ratingbot.core.common.application.logging.Loggable;

public interface SavePostRepository {
    @Transactional
    @Loggable(message = "Repository: Save post {2}, with user {3}, as an author")
    void savePost(int messageId, long chatId, long userId, String text);
}
