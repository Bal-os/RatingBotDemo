package os.balashov.ratingbot.infrastructure.sql.common.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = "event_id")
    private Long id;
    private String name;
    private String description;
    private LocalDateTime date;

    @JoinTable(
            name = "event_organizers",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "organizers_id")
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<OrganizersEntity> organizers;

    @PrimaryKeyJoinColumn(name = "location_id", referencedColumnName = "location_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LocationEntity location;

    @OneToOne(fetch = FetchType.LAZY)
    private RatingEntity rating;
}