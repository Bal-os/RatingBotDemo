package os.balashov.ratingbot.infrastructure.sql.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MessageKey implements Serializable {
    @NotNull
    @Column(name = "message_id")
    private Integer messageId;

    @NotNull
    @Column(name = "chat_id")
    private Long chatId;
}