package quiz.service;


import quiz.core.Category;
import quiz.entity.Answer;
import quiz.entity.Answered;
import quiz.entity.Player;
import quiz.entity.Question;
import quiz.service.Exception.QuestionServiceException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Transactional
@Component
public class QuestionServiceJPA implements QuestionService {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void saveQuestion(Question question) throws QuestionServiceException {
        try {
            entityManager.merge(question);
        } catch (Exception e) {
            throw new QuestionServiceException("Question was not added", e);
        }
    }

    @Override
    public Question getQuestion(int id) {
        try {
            return (Question) entityManager.createNamedQuery("Question.getQuestion").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public void incrementAnswerCount(int id) {
        Answer answer = (Answer) entityManager.createNamedQuery("Answer.getAnswer").setParameter("answerId", id).getSingleResult();
        answer.setCount(answer.getCount() + 1);
    }

    @Override
    public boolean correctAnswer(int id) {
        if (id == -1) {
            return false;
        }
        Answer answer = (Answer) entityManager.createNamedQuery("Answer.getAnswer").setParameter("answerId", id).getSingleResult();
        return answer.getCorrect();
    }

    @Override
    public int count(int id) {
        Answer answer = (Answer) entityManager.createNamedQuery("Answer.getAnswer").setParameter("answerId", id).getSingleResult();
        return answer.getCount();
    }

    @Override
    public Question getQuestion(Category category, int player_id) throws QuestionServiceException {
        if (player_id == -1) {
            List<Question> questionList = (List<Question>) entityManager.createNamedQuery("Question.getQuestions").setParameter("category", category).getResultList();
            return questionList.get(new Random().nextInt(questionList.size()));
        }

        Question resultQuestion;
        Player player = (Player) entityManager.createNamedQuery("Player.GetPlayerID").setParameter("id", player_id).getSingleResult();
        List<Question> questionList;
        try {
            List<Answered> answeredList = (List<Answered>) entityManager.createNamedQuery("Answered.getPlayerAnswered").setParameter("id", player_id).getResultList();
            questionList = (List<Question>) entityManager.createNamedQuery("Question.getQuestions").setParameter("category", category).getResultList();


            for (Question q : questionList) {
                for (Answered a : answeredList) {
                    if (q.getId() == a.getQuestion().getId()) {
                        questionList.remove(q);
                    }
                }
            }

            if (questionList.size() > 0) {
                resultQuestion = questionList.get(new Random().nextInt(questionList.size()));
            } else {
                resultQuestion = answeredList.get(answeredList.size() - 1).getQuestion();
                answeredList.get(answeredList.size() - 1).setDate(new Date());
            }


        } catch (Exception e) {
            questionList = (List<Question>) entityManager.createNamedQuery("Question.getQuestions").setParameter("category", category).getResultList();
            resultQuestion = questionList.get(new Random().nextInt(questionList.size()));
        }


        entityManager.persist(new Answered(player, resultQuestion, new Date()));

        return resultQuestion;
    }

    @Override
    public List<Category> getCategories(int count) throws QuestionServiceException {
        List<Category> categoryList;
        try {
            categoryList = (List<Category>) entityManager.createNamedQuery("Question.getCategories").getResultList();
        } catch (Exception e) {
            throw new QuestionServiceException("An error occurred in getCategories function!", e);
        }
        while (count < categoryList.size()) {
            categoryList.remove(new Random().nextInt(categoryList.size()));
        }
        return categoryList;
    }

    @Override
    public Question getEditQuestion(int idnt) throws QuestionServiceException {
        List<Question> questions;
        try {
            questions = entityManager.createNamedQuery("Question.getEditQuestion").getResultList();
        }catch (Exception e){
            throw new QuestionServiceException("An error occurred in getEditQuestion function!", e);
        }
        if(idnt < questions.size()){
            return questions.get(idnt);
        }
        else {
            return null;
        }
    }
}