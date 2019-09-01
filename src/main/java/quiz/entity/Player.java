package quiz.entity;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Player.GetPlayerFromName",
                query = "SELECT p FROM Player p WHERE p.nickName=:name AND p.password=:password"),
        @NamedQuery(name = "Player.GetPlayerID",
                query = "SELECT p FROM Player p WHERE p.playerId=:id"),
        @NamedQuery(name = "Player.login",
                query = "SELECT p FROM Player p WHERE p.email=:email AND p.password=:password"),
        @NamedQuery(name = "Player.GetPlayerFromEmail",
                query = "SELECT p FROM Player p WHERE p.email=:email")
})
public class Player {
    @Id
    @GeneratedValue
    @Column
    private int playerId;

    @Column
    private String nickName;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private int score;

    public Player() {
    }

    public Player(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void addScore(int inc) {
        score += inc;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int id) {
        this.playerId = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String name) {
        this.nickName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
