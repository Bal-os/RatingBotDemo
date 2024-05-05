package os.balashov.ratingbot.infrastructure.sql.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Organizers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organizer_id")
    private Long id;
    private String name;
    private String instagramLink;

    @OneToOne
    @JoinColumn(name = "channel_id")
    private Chat channel;

    @OneToMany(mappedBy = "organizers")
    private Set<Event> events;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "organizer_id")
    private List<UserEntity> userEntities = new LinkedList<>();
}