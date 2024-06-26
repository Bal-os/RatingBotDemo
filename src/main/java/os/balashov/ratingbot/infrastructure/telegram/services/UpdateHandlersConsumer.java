package os.balashov.ratingbot.infrastructure.telegram.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import os.balashov.ratingbot.infrastructure.telegram.hendlers.UpdateHandler;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Component
@AllArgsConstructor
public class UpdateHandlersConsumer implements LongPollingUpdateConsumer {
    private final List<UpdateHandler> handlers;
    private final ExecutorService executorService;

    @Override
    public void consume(List<Update> updates) {
        new HashSet<>(updates).forEach(update -> executorService.submit(() -> consume(update)));
    }

    public void consume(Update update) {
        handlers.stream()
                .filter(handler -> handler.canHandle(update))
                .findFirst()
                .ifPresent(handler -> handler.handleUpdate(update, calculateTraceId(update)));
    }

    private long calculateTraceId(Update update) {
        return LocalDate.now().toEpochDay() * Integer.MAX_VALUE + update.getUpdateId();
    }

}
