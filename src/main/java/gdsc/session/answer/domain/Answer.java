package gdsc.session.answer.domain;

import gdsc.session.global.BaseEntity;
import gdsc.session.question.domain.Question;
import gdsc.session.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "answers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer(
            String content,
            User author,
            Question question
    ) {
        this.content = content;
        this.author = author;
        this.question = question;
    }

    public void update(String content) {
        this.content = content;
    }
}
