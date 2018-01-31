package frank;

import org.junit.Before;
import org.junit.Test;
import ru.frank.model.UserScore;
import ru.frank.model.comparators.UserScoreComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by sbt-filippov-vv on 31.01.2018.
 */
public class UserScoreSortTest {

    static{

    }

    @Test
    public void comparableUserScoreTest(){
        ArrayList<UserScore> userScoreArrayList = new ArrayList<>();
        userScoreArrayList.add(new UserScore(1, "Vasya", 5));
        userScoreArrayList.add(new UserScore(2, "Ivan", 77));
        userScoreArrayList.add(new UserScore(3, "DumbPerson", 0));
        userScoreArrayList.add(new UserScore(4, "Olejek", 2));
        userScoreArrayList.add(new UserScore(5, "Jdenek", 1590));

        System.out.println("---BEFORE SORTING---");
        for(UserScore userScore : userScoreArrayList){
            System.out.println(userScore.toString());
        }

        Collections.sort(userScoreArrayList);

        System.out.println("---AFTER SORTING---");
        for(UserScore userScore : userScoreArrayList){
            System.out.println(userScore.toString());
        }

    }



}
