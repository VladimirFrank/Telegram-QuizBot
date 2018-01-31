package ru.frank.model.comparators;

import ru.frank.model.UserScore;

import java.util.Comparator;

/**
 * Created by sbt-filippov-vv on 31.01.2018.
 */
public class UserScoreComparator implements Comparator<UserScore> {

    @Override
    public int compare(UserScore userScoreFirst, UserScore userScoreSecond) {
        return (int) (userScoreFirst.getScore() - userScoreSecond.getScore());
    }


}
