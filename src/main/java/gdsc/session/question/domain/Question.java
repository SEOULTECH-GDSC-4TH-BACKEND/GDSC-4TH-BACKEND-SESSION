package gdsc.session.question.domain;

import gdsc.session.global.BaseEntity;
import gdsc.session.answer.domain.Answer;
import gdsc.session.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "questions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    public Question(
            String subject,
            String content,
            User author
    ) {
        this.subject = subject;
        this.content = content;
        this.author = author;
    }

    public void update(
            String subject,
            String content
    ) {
        this.subject = subject;
        this.content = content;
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }
}
