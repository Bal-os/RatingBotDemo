package os.balashov.ratingbot.infrastructure.telegram.services;

import os.balashov.ratingbot.core.common.logging.Loggable;

public interface CheckChatMember {
    @Loggable(message = "Check if user {1} is chat member in chat {2}")
    boolean isChatMember(long chatId, long userId);
}
