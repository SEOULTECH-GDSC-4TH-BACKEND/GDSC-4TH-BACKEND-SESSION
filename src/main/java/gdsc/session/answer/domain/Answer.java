package gdsc.session.answer.domain;

import gdsc.session.BaseEntity;
import gdsc.session.question.domain.Question;
import gdsc.session.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

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

    public Answer(
            String content,
            User author
    ) {
        this.content = content;
        this.author = author;
    }

    public void update(String content) {
        this.content = content;
    }
}
