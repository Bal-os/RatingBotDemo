package os.balashov.ratingbot.infrastructure.telegram.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;
import os.balashov.ratingbot.infrastructure.telegram.api.OkHttpVirtualClientCreator;
import os.balashov.ratingbot.infrastructure.telegram.consumers.UpdateHandlersConsumer;
import os.balashov.ratingbot.infrastructure.telegram.hendlers.UpdateHandler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

@Getter
@Configuration
@RequiredArgsConstructor
public class TelegramBotConfiguration implements BotConfiguration {
    private final ApplicationContext applicationContext;
    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.bot.symbol.like}")
    private String likeSymbol;
    @Value("${telegram.bot.symbol.dislike}")
    private String dislikeSymbol;

    @PostConstruct
    public void init() {
        Marks.LIKE.setSymbol(likeSymbol);
        Marks.DISLIKE.setSymbol(dislikeSymbol);
    }

    @Bean
    public UpdateHandlersConsumer updateHandlersConsumer(List<UpdateHandler> handlers,
                                                         ExecutorService executorService) {
        return new UpdateHandlersConsumer(handlers, executorService);
    }

    @Bean
    public Dispatcher dispatcher(ExecutorService executorService) {
        return new Dispatcher(executorService);
    }

    @Bean
    public TelegramClient telegramClient(Dispatcher dispatcher) {
        var client = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .build();
        return new OkHttpTelegramClient(client, botToken);
    }

    @Bean
    public TelegramBotsLongPollingApplication telegramBotsApplication(ScheduledExecutorService executorService,
                                                                      OkHttpVirtualClientCreator clientCreator) {
        return new TelegramBotsLongPollingApplication(ObjectMapper::new, clientCreator, () -> executorService);
    }
}
