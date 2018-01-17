package ru.frank.dataBaseUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import ru.frank.model.UserSession;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class UserSessionDaoImpl implements UserSessionDao{

//    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();

    @Override
    public long save(UserSession userSession) {
        session.beginTransaction();
        session.saveOrUpdate(userSession);
        session.getTransaction().commit();
        return userSession.getId();
    }

    @Override
    public UserSession get(long id) {
        session.beginTransaction();
        UserSession userSession = session.get(UserSession.class, id);
        session.getTransaction().commit();
        return userSession;
    }

    @Override
    public List<UserSession> list() {
        session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserSession> criteriaQuery = criteriaBuilder.createQuery(UserSession.class);
        Root<UserSession> root = criteriaQuery.from(UserSession.class);
        criteriaQuery.select(root);
        Query<UserSession> query = session.createQuery(criteriaQuery);
        List<UserSession> resultList = query.getResultList();
        session.getTransaction().commit();
        return resultList;
    }

    @Override
    public void update(long id, UserSession userSessionNew) {
        session.beginTransaction();
        UserSession userSession = session.byId(UserSession.class).load(id);
        userSession.setStartTime(userSessionNew.getStartTime());
        userSession.setQuestion(userSessionNew.getQuestion());
        userSession.setAnswer(userSessionNew.getAnswer());
        session.update(userSession);
        session.getTransaction().commit();
    }

    @Override
    public void delete(long id) {
        session.beginTransaction();
        UserSession userSession = session.byId(UserSession.class).load(id);
        session.delete(userSession);
        session.getTransaction().commit();
    }
}
