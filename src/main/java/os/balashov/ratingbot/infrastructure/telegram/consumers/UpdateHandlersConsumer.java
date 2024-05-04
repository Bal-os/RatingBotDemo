package os.balashov.ratingbot.infrastructure.telegram.consumers;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import os.balashov.ratingbot.infrastructure.telegram.hendlers.UpdateHandler;

import java.util.List;
import java.util.concurrent.ExecutorService;

@AllArgsConstructor
public class UpdateHandlersConsumer implements LongPollingUpdateConsumer {
    private final List<UpdateHandler> handlers;
    private final ExecutorService executor;

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(update -> executor.execute(() -> consume(update)));
    }

    public void consume(Update update) {
        handlers.stream()
                .filter(handler -> handler.canHandle(update))
                .findFirst()
                .ifPresent(handler -> handler.handleUpdate(update));
    }
}
