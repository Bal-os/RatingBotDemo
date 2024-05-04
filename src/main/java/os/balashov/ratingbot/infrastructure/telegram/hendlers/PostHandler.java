package os.balashov.ratingbot.infrastructure.telegram.hendlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import os.balashov.ratingbot.core.common.logging.Loggable;

public interface PostHandler extends UpdateHandler {
    @Override
    @Loggable(message = "Telegram handler: check if can handle post")
    default boolean canHandle(Update update) {
        return update.hasChannelPost();
    }

    @Override
    default void handleUpdate(Update update) {
        handlePost(update.getChannelPost());
    }

    @Loggable(message = "Telegram handler: handle new post")
    void handlePost(Message message);
}
