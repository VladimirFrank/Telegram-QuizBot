import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;
import ru.frank.dataBaseUtil.HibernateSessionFactory;
import ru.frank.dataBaseUtil.UserSessionDao;
import ru.frank.dataBaseUtil.UserSessionDaoImpl;
import ru.frank.model.UserSession;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by sbt-filippov-vv on 15.01.2018.
 */
public class UserSessionDatabaseTest {

    private UserSessionDao userSessionDao = new UserSessionDaoImpl();


    @Test
    public void getAllDataFromTable(){
        System.out.println("---Starting Test---");
        Transaction transaction = null;

        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserSession> criteriaQuery = builder.createQuery(UserSession.class);

            Root<UserSession> root = criteriaQuery.from(UserSession.class);
            criteriaQuery.select(root);

            Query<UserSession> query = session.createQuery(criteriaQuery);

            List<UserSession> userSessionList = query.getResultList();
            for(UserSession userSession : userSessionList){
                System.out.println(userSession.toString());
            }
        } catch (Exception ex){
            ex.printStackTrace();
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    @Test
    public void getUserSessionByIdTest(){
        System.out.println(userSessionDao.get(52145).toString());
        Assert.assertTrue(userSessionDao.get(52145).getQuestion().equals("Какой расстояние до Луны?"));
    }

    @Test
    public void saveNewUserSessionTest(){
        UserSession userSession = new UserSession((long) 555, "15:20:20", "Who is it, beach?","It's me, Holmes.");
        Assert.assertTrue(555 == userSessionDao.save(userSession));
    }

    @Test
    public void getListOfAllUserSessions(){
        List<UserSession> userSessionList = userSessionDao.list();
        for(UserSession userSession : userSessionList){
            System.out.println(userSession.toString());
        }
    }
}
