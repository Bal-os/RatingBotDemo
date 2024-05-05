package os.balashov.ratingbot.infrastructure.telegram;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import os.balashov.ratingbot.core.likesrating.ports.ChatMemberCounter;
import os.balashov.ratingbot.infrastructure.telegram.api.BotExecutor;
import os.balashov.ratingbot.infrastructure.telegram.config.TelegramBotAdaptersFactory;
import os.balashov.ratingbot.infrastructure.telegram.services.CheckAdminRole;
import os.balashov.ratingbot.infrastructure.telegram.services.CheckChatMember;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TelegramChatMemberServiceTest {
    @Mock
    private BotExecutor botExecutor;
    @InjectMocks
    private TelegramBotAdaptersFactory config;

    @Test
    public void testIsChatMember_ExistingMember() throws TelegramApiException {
        long chatId = 123L;
        long userId = 456L;
        String memberStatus = "member";
        when(botExecutor.getMemberStatus(chatId, userId)).thenReturn(Optional.of(memberStatus));
        CheckChatMember service = config.checkChatMember(botExecutor);

        boolean isMember = service.isChatMember(chatId, userId);

        assertTrue(isMember);
        verify(botExecutor, times(1)).getMemberStatus(chatId, userId);
    }

    @Test
    public void testIsChatMember_NonexistentMember() throws TelegramApiException {
        long chatId = 123L;
        long userId = 456L;
        when(botExecutor.getMemberStatus(chatId, userId)).thenReturn(Optional.empty());
        CheckChatMember service = config.checkChatMember(botExecutor);

        boolean isMember = service.isChatMember(chatId, userId);

        assertFalse(isMember);
        verify(botExecutor, times(1)).getMemberStatus(chatId, userId);
    }

    @Test
    public void testCountChatMembers() throws TelegramApiException {
        long chatId = 123L;
        int memberCount = 100;
        when(botExecutor.countMembers(chatId)).thenReturn(Optional.of(memberCount));
        ChatMemberCounter service = config.chatMemberCounter(botExecutor);

        int actualCount = service.countChatMembers(chatId);

        assertEquals(memberCount, actualCount);
        verify(botExecutor, times(1)).countMembers(chatId);
    }

    @Test
    public void testIsBotAdmin_ExistingAdmin() throws TelegramApiException {
        long chatId = 123L;
        long userId = 456L;
        String memberStatus = "administrator";
        when(botExecutor.getMemberStatus(chatId, userId)).thenReturn(Optional.of(memberStatus));
        CheckAdminRole service = config.checkAdminRole(botExecutor);

        boolean isAdmin = service.isBotAdmin(chatId, userId);

        assertTrue(isAdmin);
        verify(botExecutor).getMemberStatus(chatId, userId);
    }
}
