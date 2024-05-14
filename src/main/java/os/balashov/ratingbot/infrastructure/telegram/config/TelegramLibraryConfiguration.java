package os.balashov.ratingbot.infrastructure.telegram.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.TelegramOkHttpClientFactory;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import os.balashov.ratingbot.infrastructure.telegram.usecases.BotConfiguration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class TelegramLibraryConfiguration {
    @NotNull
    private static SpringLongPollingBot getSpringLongPollingBot(LongPollingUpdateConsumer updateConsumer, BotConfiguration botConfiguration) {
        return new SpringLongPollingBot() {
            @Override
            public String getBotToken() {
                return botConfiguration.getBotToken();
            }

            @Override
            public LongPollingUpdateConsumer getUpdatesConsumer() {
                return updateConsumer;
            }
        };
    }

    @Bean
    public Dispatcher dispatcher(ExecutorService executorService) {
        return new Dispatcher(executorService);
    }

    @Bean
    public TelegramClient telegramClient(Dispatcher dispatcher, BotConfiguration botConfiguration) {
        var telegramToken = botConfiguration.getBotToken();
        var client = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .build();
        return new OkHttpTelegramClient(client, telegramToken);
    }

    @Bean
    public TelegramBotsLongPollingApplication telegramBotsApplication(ScheduledExecutorService executorService,
                                                                      Dispatcher dispatcher) {
        var clientCreator = clientCreator(dispatcher);
        return new TelegramBotsLongPollingApplication(ObjectMapper::new, clientCreator, () -> executorService);
    }

    @Bean
    public SpringLongPollingBot springLongPollingBot(LongPollingUpdateConsumer updateConsumer, BotConfiguration botConfiguration) {
        return getSpringLongPollingBot(updateConsumer, botConfiguration);
    }

    @NotNull
    private TelegramOkHttpClientFactory.DefaultOkHttpClientCreator clientCreator(Dispatcher dispatcher) {
        return new TelegramOkHttpClientFactory.DefaultOkHttpClientCreator() {
            @Override
            public OkHttpClient get() {
                return getBaseClient().dispatcher(dispatcher).build();
            }
        };
    }
}
