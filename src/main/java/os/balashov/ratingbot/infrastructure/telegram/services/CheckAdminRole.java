package os.balashov.ratingbot.infrastructure.telegram.services;

import os.balashov.ratingbot.core.common.logging.Loggable;

public interface CheckAdminRole {
    @Loggable(message = "Check if user {1} is bot admin in chat {2}")
    boolean isBotAdmin(long chatId, long userId);
}
