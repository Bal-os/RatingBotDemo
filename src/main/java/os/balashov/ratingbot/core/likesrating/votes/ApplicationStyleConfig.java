package os.balashov.ratingbot.core.likesrating.votes;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;

@Configuration
public class ApplicationStyleConfig {
    @Value("${application.style.like}")
    private String likeSymbol;
    @Value("${application.style.dislike}")
    private String dislikeSymbol;

    @PostConstruct
    private void init() {
        Marks.LIKE.setSymbol(likeSymbol);
        Marks.DISLIKE.setSymbol(dislikeSymbol);
    }
}
