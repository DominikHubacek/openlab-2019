package quiz;

import quiz.core.Game;
import quiz.service.PlayerService;
import quiz.service.PlayerServiceJPA;
import quiz.service.QuestionService;
import quiz.service.QuestionServiceJPA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan("quiz.entity")
public class QuizServer {


    public static void main(String[] args) {
        SpringApplication.run(QuizServer.class, args);
    }


    @Bean
    public QuestionService questionService() {
        return new QuestionServiceJPA();
    }

    @Bean
    public PlayerService playerService() {
        return new PlayerServiceJPA();
    }

    @Bean
    public Game game() {
        return new Game();
    }
}
