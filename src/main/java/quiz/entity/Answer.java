package quiz.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Answer.getAnswer",
        query = "SELECT q FROM Answer q WHERE q.answerId =: answerId")
public class Answer {
    @Id
    @Column
    @GeneratedValue
    private int answerId;

    @Column
    private boolean correct;

    @Column
    private String answer;

    @Column
    private int count;

    public Answer() { }


    public void addCount() {
        count++;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public boolean getCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
