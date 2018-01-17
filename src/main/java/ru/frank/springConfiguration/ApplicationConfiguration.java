package ru.frank.springConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

}
