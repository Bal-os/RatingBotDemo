package os.balashov.ratingbot.infrastructure.telegram.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMemberCount;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramBotExecutor implements BotExecutor, KeyboardChanger {
    private final TelegramClient telegramClient;

    @Override
    public void sendPersonalMessage(long chatId, long userId, String text) { // TODO: refactor to send correct message
        SendMessage message = SendMessage.builder()
                .replyMarkup(getKeyboardLikeButtons())
                .chatId(String.valueOf(chatId))
                .text(text)
                .build();
//        try {
//            telegramClient.execute(message);
//        } catch (TelegramApiException e) {
//            log.error("Error while sending message", e);
//        }
    }

    @Override
    public Optional<String> getMemberStatus(long chatId, long userId) {
        GetChatMember getChatMember = GetChatMember.builder()
                .chatId(String.valueOf(chatId))
                .userId(userId)
                .build();
        try {
            return Optional.of(telegramClient.execute(getChatMember).getStatus());
        } catch (TelegramApiException e) {
            log.error("Telegram client executor: Error while checking chat member", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> countMembers(long chatId) {
        GetChatMemberCount getChatMemberCount = new GetChatMemberCount(String.valueOf(chatId));
        try {
            return Optional.of(telegramClient.execute(getChatMemberCount));
        } catch (TelegramApiException e) {
            log.error("Telegram client executor: Error while counting members", e);
        }
        return Optional.empty();
    }

    @Override
    public void editMessage(long chatId, int messageId, String text) {
        EditMessageText editMessageText = EditMessageText.builder()
                .replyMarkup(getKeyboardLikeButtons())
                .chatId(String.valueOf(chatId))
                .messageId(messageId)
                .text(text)
                .build();
        try {
            telegramClient.execute(editMessageText);
        } catch (TelegramApiException e) {
            log.error("Telegram client executor: Error while editing message", e);
        }
    }
}
