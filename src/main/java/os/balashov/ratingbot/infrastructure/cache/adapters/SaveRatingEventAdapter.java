package os.balashov.ratingbot.infrastructure.cache.adapters;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveRatingEvent;
import os.balashov.ratingbot.core.likesrating.events.ports.services.SaveRatingEventsService;

@Service
@AllArgsConstructor
public class SaveRatingEventAdapter implements SaveRatingEventsService {
    @Cacheable(value = "latestRatings")
    public SaveRatingEvent getEvent(int messageId, long chatId) {
        return null;
    }

    @CachePut(value = "latestRatings")
    public SaveRatingEvent saveLatestEvent(int messageId, long chatId, SaveRatingEvent rating) {
        return rating;
    }

    @CacheEvict(value = "latestRatings")
    public void removeLatestEvent(int messageId, long chatId) {

    }
}
