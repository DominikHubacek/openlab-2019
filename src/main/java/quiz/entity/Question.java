package quiz.entity;

import quiz.core.Category;

import javax.persistence.*;
import java.util.List;

@Entity

@NamedQueries({
        @NamedQuery(name = "Question.getQuestions",
                query = "SELECT q FROM Question q WHERE q.visibility = true AND q.category=:category"),
        @NamedQuery(name = "Question.getCategories",
                query = "SELECT distinct q.category FROM Question q WHERE q.visibility = true"),
        @NamedQuery(name = "Question.getQuestion",
                query = "SELECT distinct q.category FROM Question q WHERE q.id =: id"),
        @NamedQuery(name = "Question.getEditQuestion",
                query = "SELECT distinct q FROM Question q ORDER BY q.id ASC ")
})
public class Question {

    @Id
    @Column
    @GeneratedValue
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Answer> answers;

    @Column(length = 20, nullable = false)
    private Category category;

    @Column(nullable = false)
    private String content;


    @Column
    private boolean visibility;

    public Question() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
