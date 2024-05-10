package os.balashov.ratingbot.infrastructure.sql.common.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity(name = "organizers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizersEntity {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = "organizers_id")
    private Long id;
    private String name;
    private String instagramLink;

    @ManyToMany(mappedBy = "organizers", fetch = FetchType.LAZY)
    private Set<EventEntity> events;

    @OneToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity channel;

    @OneToMany
    @JoinTable(
            name = "organizers_users",
            joinColumns = @JoinColumn(name = "organizers_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> users;
}