package ru.frank.bot.botUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.frank.dataBaseUtil.QuestionAndAnswerDao;
import ru.frank.model.QuestionAndAnswer;

/**
 * Класс для получения объекта класса
 * @see QuestionAndAnswer
 * из таблицы базы данных. Основной метод: getRandomQuestionAndAnswer возвращает объект QuestionAndAnswer с данными из БД
 * по случайному id в пределах от 1 до максимального ID в БД.
 */

@Component
public class QuestionAnswerGenerator {

    @Autowired
    private QuestionAndAnswerDao questionAndAnswerDao;

    /**
     * Генерирует случайное число от 1 до Maximum ID из БД
     * @return (long) [1 ; max ID]
     */
    private long getRandomNumber(){
        return (long) (Math.random() * questionAndAnswerDao.getMaximumId() + 1);
    }

    /**
     * Метод для получения случайной записи класса QuestionAndAnswer из таблицы БД
     * @return QuestionAndAnswer object
     */
    private QuestionAndAnswer getRandomQuestionAndAnswer(){
        QuestionAndAnswer questionAndAnswer = null;

        while(questionAndAnswer == null){
            questionAndAnswer = questionAndAnswerDao.get(getRandomNumber());
        }

        return questionAndAnswer;
    }

    /**
     * Метод получает случайный объект класса QuestionAndAnswer с помощью
     * метода getRandomQuestionAndAnswer() и формирует из полей объекта QuestionAndAnswer
     * строку содержащую вопрос и ответ разделенные символом '|'.
     * Например: "В каком году началась Первая мировая война?|1914"
     *
     * @return String вопрос и ответ разделенные символом '|'.
     */
    public String getNewQuestionAndAnswerForUser(){
        StringBuilder sb = new StringBuilder();
        QuestionAndAnswer questionAndAnswer = getRandomQuestionAndAnswer();
        sb.append(questionAndAnswer.getQuestion()).append("|").append(questionAndAnswer.getAnswer());
        return sb.toString();
    }

}
