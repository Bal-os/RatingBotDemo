package os.balashov.ratingbot.infrastructure.sql.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users", indexes = {
        @Index(name = "user_id_index", columnList = "user_id", unique = true)
})
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"chat", "messages", "votes", "organizers"})
@Builder
public class UserEntity {
    @Id
    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private Set<Chat> chat;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<Message> messages;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserVote> votes;

    @JoinColumn(name = "organizer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Organizers organizers;
}
