package os.balashov.ratingbot.infrastructure.telegram.usecases;

import os.balashov.ratingbot.core.common.application.logging.Loggable;

public interface BotConfiguration {
    @Loggable(message = "Try to get bot token from configuration")
    String getBotToken();
}
