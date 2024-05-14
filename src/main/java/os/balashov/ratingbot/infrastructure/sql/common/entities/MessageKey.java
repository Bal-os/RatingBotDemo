package os.balashov.ratingbot.infrastructure.sql.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MessageKey implements Serializable {
    @Column(name = "message_id", nullable = false)
    private Integer messageId;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;
}