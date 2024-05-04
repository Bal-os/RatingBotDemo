package os.balashov.ratingbot.infrastructure.telegram.hendlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import os.balashov.ratingbot.infrastructure.telegram.api.BotExecutor;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramPostHandler implements PostHandler {  // TODO: refactor this class
    private final BotExecutor botExecutor;

    @Override
    public void handlePost(Message message) {
        long chatId = message.getChatId();
        int messageId = message.getMessageId();
        if (!message.hasText()) {
            log.info("Telegram handler: message is not text, skipping, from chatId: {}, messageId: {}", chatId, messageId);
            return;
        }
        String text = message.getText();
        log.info("Telegram handler: message {} received, from chatId: {}, messageId: {}", text, chatId, messageId);

        String updatedText = updateMessage(text); // should using external service
        botExecutor.editMessage(chatId, messageId, updatedText);
    }

    private String updateMessage(String text) { // should be without constants
        if (text.contains("Оцінка:") || text.contains("Оцінка: [1-9]*\\.?[0-9]+\\/10")) {
            log.info("Telegram handler: Message already updated: {}", text);
        } else {
            log.info("Telegram handler: Message updated: {}", text + " Оцінка: 5/10");
            text = text + "\n\nОцінка: 5/10";
        }
        return text;
    }
}
