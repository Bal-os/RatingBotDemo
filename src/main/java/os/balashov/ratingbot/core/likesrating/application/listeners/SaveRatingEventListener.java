package os.balashov.ratingbot.core.likesrating.application.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import os.balashov.ratingbot.core.common.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.application.events.SaveRatingEvent;

public interface SaveRatingEventListener {
    @Async
    @EventListener
    @Loggable(message = "EventListener: Save rating event {1}, handling...")
    void handleSaveRatingEvent(SaveRatingEvent event);
}
