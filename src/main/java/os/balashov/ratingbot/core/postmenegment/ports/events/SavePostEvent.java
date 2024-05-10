package os.balashov.ratingbot.core.postmenegment.ports.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = true)
public class SavePostEvent extends ApplicationEvent {
    private final int messageId;
    private final long chatId;
    private final long userId;
    private final String text;

    public SavePostEvent(Object source, int messageId, long chatId, long userId, String text) {
        super(source);
        this.messageId = messageId;
        this.chatId = chatId;
        this.userId = userId;
        this.text = text;
    }
}
