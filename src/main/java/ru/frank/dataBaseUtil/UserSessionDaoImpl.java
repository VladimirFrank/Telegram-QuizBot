package ru.frank.dataBaseUtil;

import org.springframework.stereotype.Repository;
import ru.frank.model.UserSession;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserSessionDaoImpl implements UserSessionDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public long save(UserSession userSession) {
        entityManager.persist(userSession);
        return userSession.getId();
    }

    @Override
    public UserSession get(long id) {
        return entityManager.find(UserSession.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserSession> list() {
        return entityManager.createQuery("from UserSession ").getResultList();
    }

    @Override
    public void update(long id, UserSession userSession) {
        entityManager.merge(userSession);
    }

    @Override
    public void delete(UserSession userSession) {
        if(entityManager.contains(userSession)){
            entityManager.remove(userSession);
        } else{
            entityManager.remove(entityManager.merge(userSession));
        }
    }
}
