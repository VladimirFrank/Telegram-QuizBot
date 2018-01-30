package ru.frank.springConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.TelegramBotsApi;
import ru.frank.bot.botUtils.QuestionAnswerGenerator;
import ru.frank.bot.botUtils.UserScoreHandler;
import ru.frank.bot.botUtils.UserSessionHandler;
import ru.frank.dataBaseUtil.QuestionAndAnswerDao;
import ru.frank.dataBaseUtil.QuestionAndAnswerDaoImpl;
import ru.frank.dataBaseUtil.UserSessionDao;
import ru.frank.dataBaseUtil.UserSessionDaoImpl;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public UserSessionDao userSessionDao(){
        return new UserSessionDaoImpl();
    }

    @Bean
    public QuestionAndAnswerDao questionAndAnswerDao(){
        return new QuestionAndAnswerDaoImpl();
    }

    @Bean
    public UserSessionHandler userSessionHandler(){
        return new UserSessionHandler();
    }

    @Bean
    public QuestionAnswerGenerator questionAnswerGenerator(){
        return new QuestionAnswerGenerator();
    }

    @Bean
    public UserScoreHandler userScoreHandler(){
        return new UserScoreHandler();
    }



    @Bean
    public TelegramBotsApi telegramBotsApi(){
        return new TelegramBotsApi();
    }
}
