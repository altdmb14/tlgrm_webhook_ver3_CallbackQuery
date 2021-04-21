package ru.tlgrmbot.myhelperbot.botapi.handlers.askdestiny;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.tlgrmbot.myhelperbot.botapi.BotState;
import ru.tlgrmbot.myhelperbot.botapi.InputMessageHandler;
import ru.tlgrmbot.myhelperbot.service.ReplyMessagesService;

import java.util.ArrayList;
import java.util.List;

/**
 * Спрашивает пользователя
 */

@Slf4j
@Component
public class AskDestinyHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;

    public AskDestinyHandler(ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_HELPER;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long chatId = inputMsg.getChatId();

        SendMessage replyToUser = messagesService.getReplyMessage(chatId, "reply.askHelper");
        replyToUser.setReplyMarkup(getInlineMessageButtons());

        return replyToUser;
    }

    private InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonYes = new InlineKeyboardButton().setText("Да");
        InlineKeyboardButton buttonNo = new InlineKeyboardButton().setText("Нет, спасибо");

        buttonYes.setCallbackData("buttonYes");
        buttonNo.setCallbackData("buttonNo");

        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(buttonYes);
        keyboardButtons.add(buttonNo);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtons);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
