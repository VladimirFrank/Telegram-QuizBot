package ru.frank.bot.botUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.frank.dataBaseUtil.UserSessionDao;
import ru.frank.model.UserSession;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
        LocalDateTime dateTime = LocalDateTime.now();
        userSessionDao.save(new UserSession(userId, dateTime.format(formatter), question, answer));
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

    /**
     * Время для ответа пользователя на вопрос = 20 секунд.
     * По истечению времени, текущая сессия должна быть удалена и пользователю отправляется сообщение об истечении
     * времени.
     * @param currentDate - время получения сообщения с ответом на вопрос от пользователя.
     * @param userId - id пользователя.
     * @return true/false.
     */
    public boolean validateDate(LocalDateTime currentDate, long userId) {
        LocalDateTime dateTimeFromSession = LocalDateTime.parse(getDateFromSession(userId), formatter);
        return currentDate.isBefore(dateTimeFromSession.plusSeconds(20));
    }





}
