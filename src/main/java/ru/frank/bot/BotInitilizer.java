package ru.frank.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class BotInitilizer {

    private RussianQuizBot russianQuizBot;
    private TelegramBotsApi telegramBotsApi;

    @Autowired
    public BotInitilizer(RussianQuizBot russianQuizBot, TelegramBotsApi telegramBotsApi){
        try{
            telegramBotsApi.registerBot(russianQuizBot);
        } catch (TelegramApiException ex){
            ex.printStackTrace();
        }

    }
}
