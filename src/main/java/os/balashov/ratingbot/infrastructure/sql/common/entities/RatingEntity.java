package os.balashov.ratingbot.infrastructure.sql.common.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "ratings")
@Table(indexes = {
        @Index(name = "rate_message_id_index", columnList = "message_id, chat_id", unique = true),
        @Index(name = "rate_event_id_index", columnList = "event_id", unique = true)
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingEntity {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private MessageKey id;
    private Integer likes;
    private Integer dislikes;
    private Double rating;

    @MapsId("id")
    @JoinColumns({
            @JoinColumn(name = "chat_id"),
            @JoinColumn(name = "message_id")
    })
    @OneToOne(fetch = FetchType.EAGER)
    private MessageEntity post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @OneToMany(mappedBy = "rating", fetch = FetchType.LAZY)
    private List<UserVote> votes;
}