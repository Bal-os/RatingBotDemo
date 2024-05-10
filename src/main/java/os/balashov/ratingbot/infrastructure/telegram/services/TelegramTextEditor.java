package os.balashov.ratingbot.infrastructure.telegram.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.infrastructure.telegram.usecases.TextEditor;

@Slf4j
@Service
public class TelegramTextEditor implements TextEditor {

    private static final String RATING_REGEX = "Оцінка: [1-9]*\\.?[0-9]+/10";
    private static final String RATING_TEXT_FORMAT = "\n\nОцінка: %s/10";
    private static final int DEFAULT_RATING = 5;

    public String updateMessage(String text) {
        if (isAlreadyRated(text)) {
            log.info("Telegram handler: Skipping update, message already has rating: {}", text);
            return text;
        }

        String defaultRatingText = String.format(RATING_TEXT_FORMAT, DEFAULT_RATING);
        log.info("Telegram handler: Updating message with default rating: {}", text);
        return text + defaultRatingText;
    }

    public String updateMessage(String text, double rating) {
        String updateString = String.format(RATING_TEXT_FORMAT, rating);
        String updatedText = text.replaceAll(RATING_REGEX, updateString.trim());
        log.info("Telegram handler: Updating message with custom rating: {}, new Text: {}", text, updatedText);
        return updatedText;
    }

    private boolean isAlreadyRated(String text) {
        return text.contains(RATING_REGEX);
    }
}