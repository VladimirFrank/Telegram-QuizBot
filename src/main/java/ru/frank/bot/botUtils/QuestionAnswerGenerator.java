package ru.frank.bot.botUtils;

import org.springframework.beans.factory.annotation.Autowired;
import ru.frank.dataBaseUtil.QuestionAndAnswerDao;
import ru.frank.model.QuestionAndAnswer;

public class QuestionAnswerGenerator {

    @Autowired
    private QuestionAndAnswerDao questionAndAnswerDao;

    private long getRandomNumber(){
        return (long) (Math.random() * questionAndAnswerDao.getRowCount());
    }
}
