import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import ru.frank.dataBaseUtil.HibernateSessionFactory;
import ru.frank.model.UserSession;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by sbt-filippov-vv on 15.01.2018.
 */
public class UserSessionDatabaseTest {

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
}
