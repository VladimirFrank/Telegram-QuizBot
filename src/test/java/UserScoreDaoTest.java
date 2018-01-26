import org.junit.Test;
import ru.frank.dataBaseUtil.userScore.UserScoreDao;
import ru.frank.dataBaseUtil.userScore.UserScoreDaoImpl;
import ru.frank.model.UserScore;

/**
 * Created by sbt-filippov-vv on 26.01.2018.
 */
public class UserScoreDaoTest {

    UserScoreDao userScoreDao = new UserScoreDaoImpl();

    @Test
    public void getByIdTest(){
        UserScore userScore = userScoreDao.get(100);
        System.out.println(userScore.getScore());
    }
}
