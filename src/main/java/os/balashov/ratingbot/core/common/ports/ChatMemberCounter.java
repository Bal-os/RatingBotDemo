package os.balashov.ratingbot.core.common.ports;

import os.balashov.ratingbot.core.common.application.logging.Loggable;

public interface ChatMemberCounter {
    @Loggable(message = "Service: Try to count chat members in chat {1}")
    int countChatMembers(long chatId);
}
