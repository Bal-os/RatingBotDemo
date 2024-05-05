package os.balashov.ratingbot.core.likesrating.application.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;

@Getter
@EqualsAndHashCode(callSuper = true)
public class SaveVoteEvent extends ApplicationEvent {
    private final int messageId;
    private final long chatId;
    private final long userId;
    private final Marks vote;

    public SaveVoteEvent(Object source, int messageId, long chatId, long userId, Marks vote) {
        super(source);
        this.messageId = messageId;
        this.chatId = chatId;
        this.userId = userId;
        this.vote = vote;
    }
}
