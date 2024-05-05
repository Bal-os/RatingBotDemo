package os.balashov.ratingbot.core.likesrating.application.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;

@Getter
@EqualsAndHashCode(callSuper = true)
public class SaveRatingEvent extends ApplicationEvent {
    private final int messageId;
    private final long chatId;
    private final PostRating postRating;
    public SaveRatingEvent(Object source, int messageId, long chatId, PostRating postRating) {
        super(source);
        this.messageId = messageId;
        this.chatId = chatId;
        this.postRating = postRating;
    }
}
