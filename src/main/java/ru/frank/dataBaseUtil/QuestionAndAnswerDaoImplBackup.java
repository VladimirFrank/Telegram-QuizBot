package ru.frank.dataBaseUtil;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import ru.frank.model.QuestionAndAnswer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by sbt-filippov-vv on 17.01.2018.
 */
public class QuestionAndAnswerDaoImplBackup{


    Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();

//    @Override
    public QuestionAndAnswer get(long id) {
        session.beginTransaction();
        QuestionAndAnswer questionAndAnswer = session.get(QuestionAndAnswer.class, id);
        session.getTransaction().commit();
        return questionAndAnswer;
    }

//    @Override
    public long getRowCount() {
        session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<QuestionAndAnswer> criteria = criteriaBuilder.createQuery(QuestionAndAnswer.class);

        criteria.from(QuestionAndAnswer.class);

        Long count = (long) session.createQuery(criteria).getResultList().size();
        session.getTransaction().commit();
        return count;
    }

//    @Override
    public long getMaximumId() {
        session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<QuestionAndAnswer> criteria = criteriaBuilder.createQuery(QuestionAndAnswer.class);

        criteria.from(QuestionAndAnswer.class);

        List<QuestionAndAnswer> questionAndAnswerList = session.createQuery(criteria).getResultList();

        long maximumId = 0;
        if(questionAndAnswerList != null && !questionAndAnswerList.isEmpty()){
            maximumId = questionAndAnswerList.get(questionAndAnswerList.size() - 1).getId();
        }
        session.getTransaction().commit();
        return maximumId;


    }
}
