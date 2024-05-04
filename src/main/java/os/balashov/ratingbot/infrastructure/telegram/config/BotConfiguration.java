package os.balashov.ratingbot.infrastructure.telegram.config;

import os.balashov.ratingbot.core.common.logging.Loggable;

public interface BotConfiguration {
    @Loggable(message = "Try to get bot token from configuration")
    String getBotToken();
}
