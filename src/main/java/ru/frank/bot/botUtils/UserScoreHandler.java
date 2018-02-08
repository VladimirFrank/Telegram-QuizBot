package ru.frank.bot.botUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.frank.dataBaseUtil.userScore.UserScoreDao;
import ru.frank.exceptions.UserScoreListIsEmptyException;
import ru.frank.model.UserScore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public void addNewUserInChart(long userId, String userName){
        userScoreDao.save(new UserScore(userId, userName, 0));
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
    
    /**
     * ArrayList include top 5 or less UserSco
     * @return
     */
    public List<UserScore> getTopFiveUserScore() {
        ArrayList<UserScore> userScoreArrayList = new ArrayList<>(userScoreDao.getAll());

        if(userScoreArrayList.isEmpty()){
            throw new UserScoreListIsEmptyException("User score list is empty.");
        }

        Collections.sort(userScoreArrayList);

        int userScoreListSize = userScoreArrayList.size();

        List<UserScore> topScoreList;

        if(userScoreListSize < 5){
            topScoreList = new ArrayList<>(userScoreArrayList.subList(0, userScoreListSize));
        } else{
            topScoreList = new ArrayList<>(userScoreArrayList.subList(0, 5));
        }

        return topScoreList;

    }

}
