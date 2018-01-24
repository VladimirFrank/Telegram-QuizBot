package ru.frank.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.frank.bot.botUtils.QuestionAnswerGenerator;
import ru.frank.bot.botUtils.UserSessionHandler;
import ru.frank.model.QuestionAndAnswer;

import javax.persistence.Column;

/**
 * Created by sbt-filippov-vv on 17.01.2018.
 */

@Component
public class RussianQuizBot extends TelegramLongPollingBot{
    private final String BOT_USER_NAME = "RussianQuizBot";
    private final String TOKEN = "480188136:AAFXnydeEfJXPt0sJnB1hjyBh-4Wpr9wrPk";

    @Autowired
    QuestionAnswerGenerator questionAnswerGenerator;

    @Autowired
    UserSessionHandler userSessionHandler;


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        long userId = message.getFrom().getId();
        if(!userSessionHandler.sessionIsActive(userId)){
            String questionAndAnswer = questionAnswerGenerator.getNewQuestionAndAnswerForUser();

            String [] questionAndAnswerArray = questionAndAnswer.split("\\|");
            String question = questionAndAnswerArray[0];


            userSessionHandler.createUserSession(userId, questionAndAnswer);
            sendMessage(message, question);
        } else if(userSessionHandler.sessionIsActive(userId) && message.getText() != null){
            String rightAnswer = userSessionHandler.getAnswerFromSession(userId).toLowerCase();

            String userAnswer = message.getText().toLowerCase();

            if(rightAnswer.contains(userAnswer)){
                sendMessage(message, "Поздравляю! Ответ правильный!");
                userSessionHandler.deleteUserSession(userId);
            } else{
                sendMessage(message, "Неверный ответ! Попробуете еще?");
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
