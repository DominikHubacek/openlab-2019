package quiz.webservice;

import quiz.core.Category;
import quiz.entity.Question;
import quiz.service.Exception.QuestionServiceException;
import quiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.Arrays;
import java.util.List;

@Path("/question")
public class QuestionServiceRest {

    @Autowired
    private QuestionService questionService;

    //http://localhost:8080/rest/question
    @POST
    @Consumes("application/json")
    public void saveQuestion(Question question) throws QuestionServiceException {
        questionService.saveQuestion(question);
    }

    @GET
    @Path("/{int}")
    @Produces("application/json")
    public List<Category> getCategories(@PathParam("int") int count) throws QuestionServiceException {
        return questionService.getCategories(count);
    }

    @GET
    @Path("/getEditQ/{id}")
    @Produces("application/json")
    public Question getEditQuestion(@PathParam("id") int idnt) throws QuestionServiceException{
        return questionService.getEditQuestion(idnt);
    }

    @GET
    @Path("/all")
    @Produces("application/json")
    public List<Category> getAllCategories() {
        return Arrays.asList(Category.values());
    }
}
