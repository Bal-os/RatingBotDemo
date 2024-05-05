package os.balashov.ratingbot.infrastructure.telegram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import os.balashov.ratingbot.core.likesrating.ports.ChatMemberCounter;
import os.balashov.ratingbot.infrastructure.telegram.TelegramMemberStatuses;
import os.balashov.ratingbot.infrastructure.telegram.api.BotExecutor;
import os.balashov.ratingbot.infrastructure.telegram.services.CheckAdminRole;
import os.balashov.ratingbot.infrastructure.telegram.services.CheckChatMember;

@Configuration
public class TelegramBotAdaptersFactory {
    @Bean
    public BotConfiguration botConfiguration(@Value("${telegram.bot.token}") String botToken) {
        return () -> botToken;
    }

    @Bean
    public ChatMemberCounter chatMemberCounter(BotExecutor botExecutor) {
        return chatId -> botExecutor.countMembers(chatId).orElse(0);
    }

    @Bean
    public CheckChatMember checkChatMember(BotExecutor botExecutor) {
        return (chatId, userId) -> botExecutor.getMemberStatus(chatId, userId)
                .map(TelegramMemberStatuses::isMemberStatus)
                .orElse(false);
    }

    @Bean
    public CheckAdminRole checkAdminRole(BotExecutor botExecutor) {
        return (chatId, userId) -> botExecutor.getMemberStatus(chatId, userId)
                .map(TelegramMemberStatuses::isAdminStatus)
                .orElse(false);
    }
}
