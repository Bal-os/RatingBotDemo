package os.balashov.ratingbot.infrastructure.sql.common.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Set;


@Entity(name = "users")
@Table(indexes = {
        @Index(name = "user_id_index", columnList = "user_id", unique = true),
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @NotNull
    @EqualsAndHashCode.Include
    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<MessageEntity> messages;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private Set<ChatEntity> chats;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserVote> votes;
}
