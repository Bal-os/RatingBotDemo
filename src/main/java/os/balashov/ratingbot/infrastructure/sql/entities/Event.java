package os.balashov.ratingbot.infrastructure.sql.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Event {
    @EmbeddedId
    private MessageKey id;

    @OneToOne
    @MapsId
    @JoinColumns({
            @JoinColumn(name = "chat_id"),
            @JoinColumn(name = "message_id")
    })
    private Message post;

    @Column(nullable = false)
    private LocalDateTime date;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organizers organizers;

    @Nullable
    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "chat_id"),
            @JoinColumn(name = "message_id")
    })
    private RatingEntity ratingEntity;
}