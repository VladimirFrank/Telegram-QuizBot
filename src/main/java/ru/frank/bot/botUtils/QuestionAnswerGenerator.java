package ru.frank.bot.botUtils;

import org.springframework.beans.factory.annotation.Autowired;
import ru.frank.dataBaseUtil.QuestionAndAnswerDao;
import ru.frank.model.QuestionAndAnswer;

/**
 * Класс для получения объекта класса
 * @see QuestionAndAnswer
 * из базы данных. Основной метод: getRandomQuestionAndAnswer возвращает объект QuestionAndAnswer с данными из БД
 * по случайному id в пределах от 1 до максимального ID в БД.
 */
public class QuestionAnswerGenerator {

    @Autowired
    private QuestionAndAnswerDao questionAndAnswerDao;

    /**
     * Генерирует случайное число от 1 до Maximum ID из БД
     * @return (long) [1 ; max ID]
     */
    private long getRandomNumber(){
        return (long) (Math.random() * questionAndAnswerDao.getRowCount() + 1);
    }

    /**
     * Метод для получения случайной записи класса QuestionAndAnswer из БД
     * @return QuestionAndAnswer object
     */
    public QuestionAndAnswer getRandomQuestionAndAnswer(){
        QuestionAndAnswer questionAndAnswer = null;

        while(questionAndAnswer == null){
            questionAndAnswer = questionAndAnswerDao.get(getRandomNumber());
        }

        return questionAndAnswer;
    }

}
