package ru.frank.bot.botUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.frank.dataBaseUtil.UserSessionDao;
import ru.frank.model.UserSession;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserSessionHandler {

    @Autowired
    UserSessionDao userSessionDao;

    public boolean sessionIsActive(Long userId){
        if(userSessionDao.get(userId) == null){
            return false;
        } else{
            return true;
        }
    }

    public void createUserSession(Long userId, String qustionAndAnswer){
        String [] questionAndAnswerArray = qustionAndAnswer.split("\\|");
        String question = questionAndAnswerArray[0];
        String answer = questionAndAnswerArray[1];
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        userSessionDao.save(new UserSession(userId, dateFormat.format(date), question, answer));
    }






}
