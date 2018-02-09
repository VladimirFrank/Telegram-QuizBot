package ru.frank.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.frank.bot.botUtils.QuestionAnswerGenerator;
import ru.frank.bot.botUtils.UserScoreHandler;
import ru.frank.bot.botUtils.UserSessionHandler;
import java.time.LocalDateTime;

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


    // TODO Сделать красиво (это уродливо), рефакторнуть на разные методы, меньше вложенных if if if.
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        long userId = message.getFrom().getId();
        String userName = message.getFrom().getUserName();

        // Ответ на пустое сообщение.
        if(message.getText() == null){
            sendMessage(message, "Для вызова помощи пришлите /help.");
            return;
        }

        String userMessageText = message.getText().toLowerCase();

        // Сессия с написавшем пользователем не активна (нет заданного вопроса викторины).
        if(!userSessionHandler.sessionIsActive(userId)){

            if(userMessageText.contains("/help")){
                sendMessage(message, "Для начала новой выкторины пришлите мне /go. Для ответа на один вопрос викторины отведено 20 секунд, " +
                        "по истечению этого времени, ответ не засчитывается. За правильный ответ засчитывается 1 балл. Для просмотра своего счета пришлите /score.");
            }

            if(userMessageText.contains("/score")){

                // Проверяем наличие текущего пользователя в таблице БД "score",
                if(userScoreHandler.userAlreadyInChart(userId)){
                    // При наличии текущего пользователя в таблице - отправляем счет игры.
                    sendMessage(message, "Ваш счет: " + String.valueOf(userScoreHandler.getUserScoreById(userId)));
                } else{
                    sendMessage(message, "Запись во вашему счету отсутствует, вероятно вы еще не играли в викторину. " +
                            "Для начала пришлите /go.");
                }

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

                sendMessage(message, question);
            // Отвечаем пользователю, если сообщение не содержит явных указаний для бота (default bot's answer)
            } else{
                sendMessage(message, "Для начала новой выкторины напишите мне /go.");
            }

        } else if(userSessionHandler.sessionIsActive(userId) && message.getText() != null){
            // Правильный ответ на вопрос викторины
            String rightAnswer = userSessionHandler.getAnswerFromSession(userId).toLowerCase();
            // Присланные пользователем ответ содержится в #userMessageText

            // Получаем текущее время для валидации сессии пользователя
            LocalDateTime currentDate = LocalDateTime.now();

            if(userSessionHandler.validateDate(currentDate, userId)){
                if(rightAnswer.contains(userMessageText)){
                    sendMessage(message, "Поздравляю! Ответ правильный! Для начала новой викторины напишите /go.");

                    // Увеличиваем счет пользователя на 1.
                    userScoreHandler.incrementUserScore(userId);
                    // Удаляем текущую сессию пользователя.
                    userSessionHandler.deleteUserSession(userId);
                } else{
                    // Неверный ответ, удаляем сессию.
                    sendMessage(message, "Неверный ответ! Попробуйте еще раз.");
                    userSessionHandler.deleteUserSession(userId);
                }

                // Сообщение по истчению 20 секунд, отведенных на сессию пользователя.
                // При этом удаляется запись в БД.
            } else{
                sendMessage(message, "Время на ответ вышло.");
                userSessionHandler.deleteUserSession(userId);
            }
        }

    }

    /**
     *
     * Send text constructed by Bot to user who's asking.
     *
     * @param message
     * @param text
     */
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
