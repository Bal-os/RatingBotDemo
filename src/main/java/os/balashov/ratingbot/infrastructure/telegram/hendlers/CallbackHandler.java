package os.balashov.ratingbot.infrastructure.telegram.hendlers;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import os.balashov.ratingbot.core.common.logging.Loggable;

public interface CallbackHandler extends UpdateHandler {
    @Override
    @Loggable(message = "Telegram handler: check if can handle callback")
    default boolean canHandle(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    default void handleUpdate(Update update) {
        handleCallback(update.getCallbackQuery());
    }

    @Loggable(message = "Telegram handler: handle new callback")
    void handleCallback(CallbackQuery callbackQuery);
}
