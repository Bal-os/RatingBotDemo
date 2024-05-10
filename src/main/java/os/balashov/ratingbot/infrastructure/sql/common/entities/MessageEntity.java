package os.balashov.ratingbot.infrastructure.sql.common.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "messages")
@Table(indexes = {
        @Index(name = "message_id_index", columnList = "chat_id, message_id"),
        @Index(name = "message_user_id_index", columnList = "user_id")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private MessageKey id;
    private String text;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @MapsId("chatId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "chat_id", insertable = false, updatable = false)
    private ChatEntity chat;
}
