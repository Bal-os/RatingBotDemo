package os.balashov.ratingbot.infrastructure.telegram.hendlers;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandler {
    boolean canHandle(Update Update);

    void handleUpdate(Update Update, Long traceId);
}
