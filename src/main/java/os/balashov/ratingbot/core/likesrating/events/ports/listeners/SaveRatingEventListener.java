package os.balashov.ratingbot.core.likesrating.events.ports.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import os.balashov.ratingbot.core.common.application.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveRatingEvent;

public interface SaveRatingEventListener {
    @Async
    @EventListener
    @Loggable(message = "EventListener: Save rating event {1}, handling...")
    void handleSaveRatingEvent(SaveRatingEvent event);
}
