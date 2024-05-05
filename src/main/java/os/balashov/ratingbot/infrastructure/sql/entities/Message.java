package os.balashov.ratingbot.infrastructure.sql.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "messages", indexes = {
        @Index(name = "message_id_index", columnList = "chat_id, message_id", unique = true)
})
public class Message {
    @EmbeddedId
    private MessageKey id;

    @ManyToOne
    @JoinColumn(name = "chat_id", insertable = false, updatable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Nullable
    private String text;
}
