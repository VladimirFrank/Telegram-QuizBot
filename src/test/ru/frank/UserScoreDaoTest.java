package frank;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.frank.Application;
import ru.frank.dataBaseUtil.userScore.UserScoreDao;
import ru.frank.dataBaseUtil.userScore.UserScoreDaoImpl;
import ru.frank.model.UserScore;



//@ContextConfiguration(classes = Application.class)
//@DataJpaTest

//@SpringBootConfiguration
//@AutoConfigurationPackage

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserScoreDaoTest {



    @Autowired
    private UserScoreDao userScoreDao;

    @Test
    public void addUserScore(){
        UserScore userScore = new UserScore(123, 553);
        userScoreDao.save(userScore);
    }

    @Test
    public void updateUserScore(){
        UserScore userScore = userScoreDao.get(123);
        userScore.setScore(100000);
        userScoreDao.update(userScore);
    }

    @Test
    public void getByIdTest(){
        long userId = 123;
        UserScore userScore = userScoreDao.get(userId);
        System.out.println("^TEST getByIdTest^" +
                "--- \n" +
                "UserScore userScore = userScoreDao.get("+ userId + ") \n" +
                "userScore.getScore() \n" +
                userScore.getScore() + "\n" +
                "---");
    }



}
