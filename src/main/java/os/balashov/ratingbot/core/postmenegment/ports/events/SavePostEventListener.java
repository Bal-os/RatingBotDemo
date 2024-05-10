package os.balashov.ratingbot.core.postmenegment.ports.events;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import os.balashov.ratingbot.core.common.application.logging.Loggable;

public interface SavePostEventListener {
    @Async
    @EventListener
    @Loggable(message = "EventListener: Save post event {1}, handling...")
    void handleSavePostEvent(SavePostEvent event);
}
