package os.balashov.ratingbot.infrastructure.sql.common.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;

@Entity(name = "users_posts_votes")
@Table(indexes = {
        @Index(name = "vote_user_id_index", columnList = "user_id, chat_id, message_id", unique = true),
        @Index(name = "vote_message_id_index", columnList = "chat_id, message_id", unique = true)
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVote {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = "vote_id")
    private Long id;

    @Column(nullable = false)
    private Marks vote;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "chat_id"),
            @JoinColumn(name = "message_id")
    })
    private RatingEntity rating;
}
