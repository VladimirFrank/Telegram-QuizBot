package ru.frank.dataBaseUtil;

import org.springframework.stereotype.Component;
import ru.frank.model.QuestionAndAnswer;

@Component
public interface QuestionAndAnswerDao {

    QuestionAndAnswer get(long id);

    long getRowCount();

    long getMaximumId();


}
