package os.balashov.ratingbot.core.postmenegment.ports.usecases;

import os.balashov.ratingbot.core.common.application.logging.Loggable;

public interface UpdatePost {
    @Loggable(message = "UseCase: {1}, with user {2}, as an author")
    void updatePost(int messageId, long chatId, long userId, String text);
}
