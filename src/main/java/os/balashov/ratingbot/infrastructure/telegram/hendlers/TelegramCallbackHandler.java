package os.balashov.ratingbot.infrastructure.telegram.hendlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import os.balashov.ratingbot.core.likesrating.ports.CheckUserVote;
import os.balashov.ratingbot.core.likesrating.ports.ProcessVoteAndUpdate;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;
import os.balashov.ratingbot.infrastructure.telegram.api.BotExecutor;
import os.balashov.ratingbot.infrastructure.telegram.services.CheckChatMember;
import os.balashov.ratingbot.infrastructure.telegram.services.TextEditor;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramCallbackHandler implements CallbackHandler {
    private final BotExecutor botExecutor;
    private final CheckChatMember checkChatMember;
    private final ProcessVoteAndUpdate processVoteAndUpdate;
    private final CheckUserVote checkUserVote;
    private final TextEditor textEditor;

    @Override
    public void handleCallback(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        long userId = callbackQuery.getFrom().getId();
        int messageId = callbackQuery.getMessage().getMessageId();
        var callbackQueryId = callbackQuery.getId();

        var vote = Marks.from(callbackQuery.getData());

        if (!checkChatMember.isChatMember(chatId, userId)) {
            log.info("Telegram handler: user {} is not a member of the chat {} but tried to vote", userId, chatId);
            botExecutor.sendPersonalMessage(chatId, userId, callbackQueryId, "You must be a member of the chat to vote.");
            return;
        }
        if (checkUserVote.isUserVoted(messageId, chatId, userId)
                && checkUserVote.isVoteNotChanged(messageId, chatId, userId, vote)) {
            log.info("Telegram handler: user {} has already voted for message {}", userId, messageId);
            botExecutor.sendPersonalMessage(chatId, userId, callbackQueryId, "You have already voted.");
            return;
        }

        log.info("Telegram handler: user {} voted for message {} with data {}", userId, messageId, vote.data());
        double rating = processVoteAndUpdate.processVoteAndUpdate(messageId, chatId, userId, vote);
        var message = (Message) callbackQuery.getMessage();
        var text = message.getText();
        var updatedText = textEditor.updateMessage(text, rating);

        if (text.equals(updatedText)) {
            log.info("Telegram handler: user {} has already voted for message {}", userId, messageId);
            botExecutor.sendPersonalMessage(chatId, userId, callbackQueryId, "You have already voted.");
            return;
        }

        log.info("Telegram handler: message {} should be changed with text {}", messageId, updatedText);
        botExecutor.editMessage(chatId, messageId, updatedText);
        botExecutor.sendPersonalMessage(chatId, userId, callbackQueryId, "Thank you for your vote!");
    }
}

