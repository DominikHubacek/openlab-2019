package quiz.core;

import quiz.entity.Player;
import quiz.entity.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private static Map<Integer, Game> map = new HashMap<>();
    private List<Question> questions = new ArrayList<>();
    private Player player;


    public static Map<Integer, Game> getMap() {
        return map;
    }

    public static void setMap(Map<Integer, Game> map) {
        Game.map = map;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

