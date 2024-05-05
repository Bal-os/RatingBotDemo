package os.balashov.ratingbot.core.common.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;

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
