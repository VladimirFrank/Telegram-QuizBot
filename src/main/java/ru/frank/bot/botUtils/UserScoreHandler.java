package ru.frank.bot.botUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.frank.dataBaseUtil.userScore.UserScoreDao;
import ru.frank.model.UserScore;

/**
 * Класс для обработки событий связанных с чтением, изменением, дополнением
 * счета пользователя в таблице базы данных.
 */
@Component
public class UserScoreHandler {

    @Autowired
    UserScoreDao userScoreDao;

    /**
     * Метод проверяет наличие пользователя в таблице базы данных "user_score";
     * @param userId
     * @return true - если пользователь уже есть в таблице, false - если нет.
     */
    public boolean userAlreadyInChart(long userId){
        return userScoreDao.get(userId) != null;
    }

    /**
     * Метод добавляет новую запись в таблицу
     * @param userId
     */
    public void addNewUserInChart(long userId){
        userScoreDao.save(new UserScore(userId, 0));
    }

    public long incrementUserScore(long userId){
        UserScore userScore = userScoreDao.get(userId);
        userScore.setScore(userScore.getScore() + 1);
        userScoreDao.update(userScore);
        return userScore.getScore();
    }

    public long getUserScoreById(long userId){
        return userScoreDao.get(userId).getScore();
    }

}
