package os.balashov.ratingbot.infrastructure.sql.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;

@Data
@Entity
@Table(name = "user_post_vote", indexes = {
        @Index(name = "user_id_index", columnList = "user_id", unique = true)
})
@Builder
@ToString(exclude = {"userEntity", "ratingEntity"})
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class UserVote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "chat_id"),
            @JoinColumn(name = "message_id")
    })
    @Nullable
    private RatingEntity ratingEntity;

    @NotNull
    private Marks vote;
}
