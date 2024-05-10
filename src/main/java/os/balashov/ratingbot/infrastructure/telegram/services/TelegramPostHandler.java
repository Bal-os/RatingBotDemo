package os.balashov.ratingbot.infrastructure.telegram.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import os.balashov.ratingbot.core.postmenegment.ports.usecases.ProcessPostCreation;
import os.balashov.ratingbot.infrastructure.telegram.usecases.BotExecutor;
import os.balashov.ratingbot.infrastructure.telegram.hendlers.PostHandler;
import os.balashov.ratingbot.infrastructure.telegram.usecases.TextEditor;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramPostHandler implements PostHandler {
    private final ProcessPostCreation processPostCreation;
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
        long userId = message.getSenderChat().getId();
        processPostCreation.addPost(messageId, chatId, userId, updatedText);
        botExecutor.editMessage(chatId, messageId, updatedText);
    }
}
