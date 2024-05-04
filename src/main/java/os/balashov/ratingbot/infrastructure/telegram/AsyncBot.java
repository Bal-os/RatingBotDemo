package os.balashov.ratingbot.infrastructure.telegram;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import os.balashov.ratingbot.infrastructure.telegram.config.BotConfiguration;

@Component
@AllArgsConstructor
public class AsyncBot implements SpringLongPollingBot {
    private final LongPollingUpdateConsumer updateConsumer;
    private final BotConfiguration botConfiguration;

    @Override
    public String getBotToken() {
        return botConfiguration.getBotToken();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return updateConsumer;
    }
}
