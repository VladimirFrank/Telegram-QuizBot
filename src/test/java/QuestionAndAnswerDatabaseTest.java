import org.junit.Assert;
import org.junit.Test;
import ru.frank.dataBaseUtil.QuestionAndAnswerDao;
import ru.frank.dataBaseUtil.QuestionAndAnswerDaoImpl;
import ru.frank.model.QuestionAndAnswer;

/**
 * Created by sbt-filippov-vv on 17.01.2018.
 */
public class QuestionAndAnswerDatabaseTest {

    private QuestionAndAnswerDao questionAndAnswerDao = new QuestionAndAnswerDaoImpl();

    @Test
    public void getQuestionAndAnswerTest(){
        QuestionAndAnswer questionAndAnswer = questionAndAnswerDao.get(1);
        System.out.println(questionAndAnswer.toString());
    }

    @Test
    public void getRowCoungTest(){
        long rowCount = questionAndAnswerDao.getRowCount();
        System.out.println(rowCount);
        Assert.assertTrue(rowCount == 3);

    }
}
