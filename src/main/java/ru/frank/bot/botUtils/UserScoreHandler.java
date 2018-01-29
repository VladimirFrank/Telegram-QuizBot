package ru.frank.bot.botUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.frank.dataBaseUtil.userScore.UserScoreDao;
import ru.frank.model.UserScore;

@Component
public class UserScoreHandler {

    @Autowired
    UserScoreDao userScoreDao;

    public boolean userAlreadyInChart(long userId){
        return userScoreDao.get(userId) != null;
    }

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
