package os.balashov.ratingbot.infrastructure.telegram.usecases;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import os.balashov.ratingbot.core.likesrating.votes.ports.entities.Marks;

public interface KeyboardChanger {
    default InlineKeyboardMarkup getKeyboardLikeButtons() {
        InlineKeyboardButton likeButton = new InlineKeyboardButton(Marks.LIKE.symbol());
        InlineKeyboardButton dislikeButton = new InlineKeyboardButton(Marks.DISLIKE.symbol());
        likeButton.setCallbackData(Marks.LIKE.data());
        dislikeButton.setCallbackData(Marks.DISLIKE.data());
        InlineKeyboardRow keyboardRow = new InlineKeyboardRow(
                likeButton,
                dislikeButton
        );
        return InlineKeyboardMarkup.builder().keyboardRow(keyboardRow).build();
    }
}
