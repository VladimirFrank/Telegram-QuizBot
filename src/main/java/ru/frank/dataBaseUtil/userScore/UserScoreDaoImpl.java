package ru.frank.dataBaseUtil.userScore;

import org.springframework.stereotype.Repository;
import ru.frank.model.UserScore;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserScoreDaoImpl implements UserScoreDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public UserScore get(long id) {
        return entityManager.find(UserScore.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserScore> getAll() {
        return entityManager.createQuery("from UserScore").getResultList();
    }

    @Override
    public long save(UserScore userScore) {
        entityManager.persist(userScore);
        return userScore.getId();
    }

    @Override
    public void update(UserScore userScore) {
        entityManager.merge(userScore);
    }
}
