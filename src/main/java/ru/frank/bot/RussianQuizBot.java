package ru.frank.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.frank.bot.botUtils.QuestionAnswerGenerator;
import ru.frank.bot.botUtils.UserScoreHandler;
import ru.frank.bot.botUtils.UserSessionHandler;
import ru.frank.model.UserScore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RussianQuizBot extends TelegramLongPollingBot{
    private final String BOT_USER_NAME = "RussianQuizBot";
    private final String TOKEN = "480188136:AAFXnydeEfJXPt0sJnB1hjyBh-4Wpr9wrPk";

    @Autowired
    QuestionAnswerGenerator questionAnswerGenerator;

    @Autowired
    UserSessionHandler userSessionHandler;

    @Autowired
    UserScoreHandler userScoreHandler;


    // TODO Сделать красиво (это уродливо), рефакторнуть на разные методы.
    // TODO Меньше вложенных if if if.
    @Override
    public void onUpdateReceived(Update update){

        Message message = null;

        //Текст сообщения от пользователя
        String userMessageText = null;
        String userName = null;

        Long userId = null;
        Long chatId = null;

        // Get text message from user.
        if(update.hasMessage()){
            // Answer for empty user's message.
            if(!update.getMessage().hasText() & !update.hasCallbackQuery()){
                executeSendMainMenu(update.getMessage().getChatId());
                return;
            }
            message = update.getMessage();
            userId = message.getFrom().getId().longValue();
            userName = message.getFrom().getUserName();
            chatId = message.getChatId();
            userMessageText = message.getText().toLowerCase();
//            // TODO Заменить на отдельный метод
//            sendMessage.setChatId(chatId);

            if(!userSessionHandler.sessionIsActive(userId)){
                executeSendMainMenu(chatId);
            }

        }

        // Get pressed button from user.
        if(update.hasCallbackQuery()){
            message = update.getCallbackQuery().getMessage();
            userMessageText = update.getCallbackQuery().getData();
            chatId = message.getChatId();
            userId = update.getCallbackQuery().getFrom().getId().longValue();
        }

        // Сессия с написавшем пользователем не активна (нет заданного вопроса викторины).
        if(!userSessionHandler.sessionIsActive(userId) && userMessageText != null){

            if(userMessageText.contains("/help")){
                executeSendTextMessage(chatId, "Для начала новой выкторины пришлите мне /go. " +
                        "Для ответа на один вопрос викторины отведено 20 секунд, " +
                        "по истечению этого времени, ответ не засчитывается. " +
                        "За правильный ответ засчитывается 1 балл. " +
                        "Для просмотра своего счета пришлите /score.");
            }

            if(userMessageText.contains("/score")){

                // Проверяем наличие текущего пользователя в таблице БД "score",
                if(userScoreHandler.userAlreadyInChart(userId)){
                    executeSendTextMessage(chatId, "Ваш счет: " + String.valueOf(userScoreHandler.getUserScoreById(userId)));
//                    // При наличии текущего пользователя в таблице - отправляем счет игры.
//                    sendMessage(message, "Ваш счет: " + String.valueOf(userScoreHandler.getUserScoreById(userId)));
                } else{
                    executeSendTextMessage(chatId, "Запись во вашему счету отсутствует, " +
                            "вероятно вы еще не играли в викторину. " +
                            "Для начала пришлите /go.");
//                    sendMessage(message, "Запись во вашему счету отсутствует, " +
//                            "вероятно вы еще не играли в викторину. " +
//                            "Для начала пришлите /go.");
                }


            }

            if(userMessageText.contains("/top10")){
                List<UserScore> topUsersScoreList = userScoreHandler.getTopFiveUserScore();
                String topUsersScoreString = topUsersScoreList.stream()
                        .map(UserScore::getUserName)
                        .collect(Collectors.joining("\n"));
                executeSendTextMessage(chatId, topUsersScoreString);

//
//                try {
//                    execute(sendMessage.setText(topUsersScoreString));
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }

            }

            // Начало новой викторины.
            if(userMessageText.contains("/go")){

                // Получаем новый вопрос + ответ из генератора в виде одной строки.
                String questionAndAnswer = questionAnswerGenerator.getNewQuestionAndAnswerForUser();

                String [] questionAndAnswerArray = questionAndAnswer.split("\\|");
                String question = questionAndAnswerArray[0];

                // Создаем сессию с текущим пользователем
                userSessionHandler.createUserSession(userId, questionAndAnswer);

                // Проверяем наличие текущего пользователя в таблице БД "score",
                // при отсутствии - добавляем пользователя в таблицу со счетом 0.
                if(!userScoreHandler.userAlreadyInChart(userId)){
                    userScoreHandler.addNewUserInChart(userId, userName);
                }

                executeSendTextMessage(chatId, question);
                ////sendMessage(message, question);
            // Отвечаем пользователю, если сообщение не содержит явных указаний для бота (default bot's answer)
            }
            //else{
            //    executeSendMainMenu(chatId);
            //}

        } else if(userSessionHandler.sessionIsActive(userId) && userMessageText != null){
            // Правильный ответ на вопрос викторины
            String rightAnswer = userSessionHandler.getAnswerFromSession(userId).toLowerCase();
            // Присланные пользователем ответ содержится в #userMessageText

            // Получаем текущее время для валидации сессии пользователя
            LocalDateTime currentDate = LocalDateTime.now();

            if(userSessionHandler.validateDate(currentDate, userId)){
                if(rightAnswer.contains(userMessageText)){
                    executeSendTextMessage(chatId, "Поздравляю! Ответ правильный! Для начала новой викторины напишите /go.");
//                    sendMessage(message, "Поздравляю! Ответ правильный! Для начала новой викторины напишите /go.");

                    // Увеличиваем счет пользователя на 1.
                    userScoreHandler.incrementUserScore(userId);
                    // Удаляем текущую сессию пользователя.
                    userSessionHandler.deleteUserSession(userId);
                } else{
                    // Неверный ответ, удаляем сессию.
                    executeSendTextMessage(chatId, "Неверный ответ! Попробуйте еще раз.");
                    userSessionHandler.deleteUserSession(userId);
                }

                // Сообщение по истчению 20 секунд, отведенных на сессию пользователя.
                // При этом удаляется запись в БД.
            } else{
                executeSendTextMessage(chatId, "Время на ответ вышло.");
//                sendMessage(message, "Время на ответ вышло.");
                userSessionHandler.deleteUserSession(userId);
            }
        }

    }

    private void executeSendMainMenu(Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выбери команду");
        sendMessage.setReplyMarkup(getMainBotMarkup());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void executeSendTextMessage(Long chatId, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method builds main bot menu buttons that contains basic bot commands.
     * @return InlineKeyboardMarkup object with build menu.
     */
    private InlineKeyboardMarkup getMainBotMarkup(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        List<InlineKeyboardButton> fourthRow = new ArrayList<>();
        firstRow.add(new InlineKeyboardButton().setText("Начать игру").setCallbackData("/go"));
        secondRow.add(new InlineKeyboardButton().setText("Мой счет").setCallbackData("/score"));
        thirdRow.add(new InlineKeyboardButton().setText("Топ 10").setCallbackData("/top10"));
        fourthRow.add(new InlineKeyboardButton().setText("Помощь").setCallbackData("/help"));
        rowsInLine.add(firstRow);
        rowsInLine.add(secondRow);
        rowsInLine.add(thirdRow);
        rowsInLine.add(fourthRow);
        markupInline.setKeyboard(rowsInLine);
        return markupInline;
    }

    /**
     *
     * Send text constructed by Bot to user who's asking.
     *
     * @param message
     * @param text
     */
    @Deprecated
    private void sendMessage(Message message, String text){
        // Temporary verification
        // TODO Replace verification to finding method
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            sendMessage(sendMessage);
        } catch (TelegramApiException ex){
            ex.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return this.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return this.TOKEN;
    }
}
