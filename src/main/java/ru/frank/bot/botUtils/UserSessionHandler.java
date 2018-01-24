package ru.frank.bot.botUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.frank.dataBaseUtil.UserSessionDao;
import ru.frank.model.UserSession;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class UserSessionHandler {

    @Autowired
    UserSessionDao userSessionDao;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");

    public boolean sessionIsActive(Long userId){
        if(userSessionDao.get(userId) == null){
            return false;
        } else{
            return true;
        }
    }

    public void createUserSession(Long userId, String questionAndAnswer){
        String [] questionAndAnswerArray = questionAndAnswer.split("\\|");
        String question = questionAndAnswerArray[0];
        String answer = questionAndAnswerArray[1];
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        userSessionDao.save(new UserSession(userId, dateFormat.format(date), question, answer));
    }

    public String getQuestionAndAnswerFromDB(long userId){
        StringBuilder sb = new StringBuilder();
        UserSession userSession = userSessionDao.get(userId);
        sb.append(userSession.getQuestion()).append(userSession.getAnswer());
        return sb.toString();
    }

    public String getAnswerFromSession(long userId){
        UserSession userSession = userSessionDao.get(userId);
        return userSession.getAnswer();
    }

    public void deleteUserSession(long userId){
        userSessionDao.delete(userSessionDao.get(userId));
    }

    private String getDateFromSession(long userId){
        UserSession userSession = userSessionDao.get(userId);
        return userSession.getStartTime();
    }

    public boolean validateDate(Date currentDate, long userId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String currentDateString = dateFormat.format(currentDate);
        String dateFromSession = getDateFromSession(userId);

//        if(currentDate.equals(dateFromSession))
//    }
        return false;

    }





}
