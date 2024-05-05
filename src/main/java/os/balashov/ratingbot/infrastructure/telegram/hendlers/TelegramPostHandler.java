package os.balashov.ratingbot.infrastructure.telegram.hendlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import os.balashov.ratingbot.infrastructure.telegram.api.BotExecutor;
import os.balashov.ratingbot.infrastructure.telegram.services.TextEditor;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramPostHandler implements PostHandler {
    private final BotExecutor botExecutor;
    private final TextEditor textEditor;

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

        String updatedText = textEditor.updateMessage(text);
        botExecutor.editMessage(chatId, messageId, updatedText);
    }
}
