package quiz.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries(
        @NamedQuery(name = "Answered.getPlayerAnswered",
                query = "select a from Answered a where a.player.playerId =: id order by a.date")

)
public class Answered {

    @Id
    @GeneratedValue
    @Column
    private int id;

    @OneToOne
    private Player player;

    @OneToOne
    private Question question;

    @Column
    private Date date;

    public Answered() {
    }

    public Answered(Player player, Question question, Date date) {
        this.player = player;
        this.question = question;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
