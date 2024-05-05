package os.balashov.ratingbot.infrastructure.sql.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "chats", indexes = {
        @Index(name = "chat_id_index", columnList = "chatId", unique = true)
})
public class Chat {
    @Id
    @NotNull
    @Column(name = "chatId")
    private Long chatId;

    private String name;
    private String description;
    private String type;

    @ManyToMany
    @JoinTable(
            name = "chat_users",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> members;

    @OneToMany(mappedBy = "chat")
    private Set<Message> messages;
}
