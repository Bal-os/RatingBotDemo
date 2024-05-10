package os.balashov.ratingbot.core.likesrating.events.ports.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import os.balashov.ratingbot.core.common.application.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveVoteEvent;

public interface SaveVoteEventListener {
    @Async
    @EventListener
    @Loggable(message = "EventListener: Save vote event {1}, handling...")
    void handleSaveVoteEvent(SaveVoteEvent event);
}
