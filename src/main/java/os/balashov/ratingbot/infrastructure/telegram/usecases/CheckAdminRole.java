package os.balashov.ratingbot.infrastructure.telegram.usecases;

import os.balashov.ratingbot.core.common.application.logging.Loggable;

public interface CheckAdminRole {
    @Loggable(message = "Check if user {1} is bot admin in chat {2}")
    boolean isBotAdmin(long chatId, long userId);
}
