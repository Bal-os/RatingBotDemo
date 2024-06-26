package os.balashov.ratingbot.infrastructure.telegram.usecases;

import os.balashov.ratingbot.core.common.application.logging.Loggable;

import java.util.Optional;

public interface BotExecutor {
    @Loggable(message = "Telegram client executor: Send message to chat {1}")
    void sendPersonalMessage(long chatId, long userId, String callbackQueryId, String text);

    @Loggable(message = "Telegram client executor: Get user {2} status in chat {1}")
    Optional<String> getMemberStatus(long chatId, long userId);

    @Loggable(message = "Telegram client executor: Count members in chat {1}")
    Optional<Integer> countMembers(long chatId);

    @Loggable(message = "Telegram client executor: Edit message {2} in chat {1}")
    void editMessage(long chatId, int messageId, String text);
}
