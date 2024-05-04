package os.balashov.ratingbot.infrastructure.telegram.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.likesrating.ports.ChatMemberCounter;
import os.balashov.ratingbot.infrastructure.telegram.api.BotExecutor;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramChatMemberService implements CheckChatMember, ChatMemberCounter, CheckAdminRole { // TODO: check
    private final BotExecutor botExecutor;

    @Override
    public boolean isChatMember(long chatId, long userId) {
        try {
            return botExecutor.getMemberStatus(chatId, userId)
                    .map(this::isMemberStatus)
                    .orElse(false);
        } catch (Exception e) {
            log.error("Telegram Service: Error while checking member status: {}", e.getMessage());
            return false;
        }
    }

    private boolean isMemberStatus(String status) {
        return !"left".equals(status) && !"kicked".equals(status) && !"restricted".equals(status);
    }

    @Override
    public int countChatMembers(long chatId) {
        try {
            return botExecutor.countMembers(chatId).orElse(0);
        } catch (Exception e) {
            log.error("Telegram Service: Error while counting chat members: {}", e.getMessage());
            return 0;
        }
    }

    private boolean isAdminStatus(String status) {
        return "creator".equals(status) || "administrator".equals(status);
    }

    @Override
    public boolean isBotAdmin(long chatId, long userId) {
        try {
            return botExecutor.getMemberStatus(chatId, userId)
                    .map(this::isAdminStatus)
                    .orElse(false);
        } catch (Exception e) {
            log.error("Telegram Service: Error while checking admin status: {}", e.getMessage());
            return false;
        }
    }
}
