package os.balashov.ratingbot.infrastructure.sql.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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