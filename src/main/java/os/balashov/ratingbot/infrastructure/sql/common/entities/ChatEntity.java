package os.balashov.ratingbot.infrastructure.sql.common.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "chats")
@Table(indexes = {
        @Index(name = "chat_id_index", columnList = "chat_id", unique = true)
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntity {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "chat_id")
    private Long chatId;
    private String name;
    private String description;
    private String type;

    @OneToMany(mappedBy = "chat")
    private Set<MessageEntity> messages;

    @JoinTable(
            name = "chat_members",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<UserEntity> members;
}
