package quiz.service;

import quiz.core.Category;
import quiz.entity.Question;
import quiz.service.Exception.QuestionServiceException;

import java.util.List;

public interface QuestionService {
    //Prototype, this class will be changed  @Viktor
    void saveQuestion(Question question) throws QuestionServiceException;

    Question getQuestion(int id);

    Question getQuestion(Category category, int player_id) throws QuestionServiceException;

    List<Category> getCategories(int count) throws QuestionServiceException;

    Question getEditQuestion(int idnt) throws QuestionServiceException;

    void incrementAnswerCount(int id);

    boolean correctAnswer(int id);

    int count(int id);

}
