package quiz.controller;


import quiz.core.Category;
import quiz.core.Game;
import quiz.entity.Answer;
import quiz.core.Lights;
import quiz.entity.Player;
import quiz.entity.Question;
import quiz.service.Exception.PlayerServiceException;
import quiz.service.Exception.QuestionServiceException;
import quiz.service.PlayerService;
import quiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class QuizController {
    private static int connectionId = 1;

    @Autowired
    private Game game;

    private Question currentQuestion;

    private int counterQuestion = 0;

    private int counterCategories = 0;

    private boolean help;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("")
    public String categories(Model model) {
        model.addAttribute("id", connectionId++);
        return "index";
    }

    @RequestMapping("controller")
    public String controller(Model model, @RequestParam Integer id){
        model.addAttribute("id", id);
        return "controller";
    }

    @RequestMapping("/getquestion")
    public String getQuestion(@RequestParam String category, Model model) throws QuestionServiceException {
        if (counterQuestion == 3 && counterCategories == 2) {
            counterQuestion = 0;

            model.addAttribute("player", game.getPlayer());

            return "end";
        } else if (counterQuestion == 3) {
            counterQuestion = 0;
            counterCategories++;
            help = true;
            return "categories";
        }

        currentQuestion = questionService.getQuestion(Category.valueOf(category), game.getPlayer().getPlayerId());
        game.addQuestion(currentQuestion);
        model.addAttribute("player", game.getPlayer());
        model.addAttribute("question", game.getQuestions().get(game.getQuestions().size() - 1));
        model.addAttribute("help", help);
        counterQuestion++;
        return "question";
    }

    @RequestMapping("/end")
    public String end(Model model) throws QuestionServiceException {
        model.addAttribute("player", game.getPlayer());
        return "end";
    }

    @RequestMapping("/categories")
    public String categories() {
        help = true;
        return "categories";
    }

    @RequestMapping("/form")
    public String form() {
        return "form";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public boolean login(@RequestParam String email, @RequestParam String pass, @RequestParam String method, @RequestParam String name) throws PlayerServiceException {
        switch (method) {
            case "form":
                try {
                    playerService.login(email, pass);
                    this.game.setPlayer(playerService.login(email, pass));
                } catch (Exception e) {
                    this.game.setPlayer(null);
                }
                break;
            case "google":
                try {
                    this.game.setPlayer(playerService.getPlayerFromMail(email));
                } catch (Exception e) {
                    Player player = new Player(email, pass);
                    player.setNickName(name);
                    playerService.register(player);
                    this.game.setPlayer(player);
                }
                break;
            case "none":
                Player player = new Player();
                player.setPlayerId(-1);
                this.game.setPlayer(player);
                break;
        }
        return this.game.getPlayer() != null;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public boolean register(@RequestParam(required = true) String email, @RequestParam(required = true) String name, @RequestParam(required = true) String pass) {
        try {
            Player player = new Player(email, pass);
            player.setNickName(name);
            playerService.register(player);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    @ResponseBody
    public void answer(@RequestParam(required = true) int id) {
        questionService.incrementAnswerCount(id);
    }


    @RequestMapping(value = "/correct", method = RequestMethod.GET)
    @ResponseBody
    public boolean isCorrectAnswer(@RequestParam(required = true) int id) {
        return questionService.correctAnswer(id);
    }


    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ResponseBody
    public int count(@RequestParam(required = true) int id) {
        return questionService.count(id);
    }


    @RequestMapping(value = "/fifty-fifty", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Integer> fifty_fifty() {
        if (!help) {
            return null;
        }
        help = false;
        var answers = currentQuestion.getAnswers();
        for (Answer a : answers) {
            if (a.getCorrect()) {
                Answer wrong;
                do {
                    wrong = answers.get(new Random().nextInt(4));
                } while (wrong == a);
                var ret = new ArrayList<Integer>();
                ret.add(a.getAnswerId());
                ret.add(wrong.getAnswerId());
                return ret;
            }
        }
        return null;
    }

    @RequestMapping(value = "/selectAnswer", method = RequestMethod.GET)
    @ResponseBody
    public int select(int id) {
        Lights lights = new Lights();

        for (Answer a : currentQuestion.getAnswers()) {
            if (a.getAnswerId() == id) {
                a.addCount();
            }
        }

        if (isCorrectAnswer(id)) {
            this.game.getPlayer().addScore(5);
            lights.turnOn("green");
        } else {
            lights.turnOn("red");
        }

        for (Answer a : currentQuestion.getAnswers()) {
            if (a.getCorrect()) {
                return a.getAnswerId();
            }
        }
        return -1;
    }

}

