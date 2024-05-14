package os.balashov.ratingbot.infrastructure.sql.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "locations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = "location_id")
    private Long id;
    private String name;
    private String address;
    private String googleMapsLink;
    private String instagramLink;
    private String telegramLink;
    private String number;
    private String type;
}