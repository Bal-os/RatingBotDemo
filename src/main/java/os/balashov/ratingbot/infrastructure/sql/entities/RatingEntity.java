package os.balashov.ratingbot.infrastructure.sql.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity(name = "rating")
@Table(indexes = {
        @Index(name = "message_id_index", columnList = "message_id", unique = true)
})
@Builder
@ToString(exclude = {"event", "votes"})
@NoArgsConstructor
@AllArgsConstructor
public class RatingEntity {
    @EmbeddedId
    private MessageKey id;

    @OneToOne(mappedBy = "ratingEntity", fetch = FetchType.LAZY)
    private Event event;

    private Integer likes;
    private Integer dislikes;
    private Double rating;

    @OneToMany(mappedBy = "ratingEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserVote> votes;
}