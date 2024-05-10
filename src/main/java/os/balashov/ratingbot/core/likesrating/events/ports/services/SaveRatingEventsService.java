package os.balashov.ratingbot.core.likesrating.events.ports.services;

import os.balashov.ratingbot.core.likesrating.events.ports.entities.SaveRatingEvent;

public interface SaveRatingEventsService {
    SaveRatingEvent getEvent(int messageId, long chatId);
    SaveRatingEvent saveLatestEvent(int messageId, long chatId, SaveRatingEvent rating);
    void removeLatestEvent(int messageId, long chatId);
}
