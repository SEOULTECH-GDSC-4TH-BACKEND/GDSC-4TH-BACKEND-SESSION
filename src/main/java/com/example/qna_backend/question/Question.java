package com.example.qna_backend.question;

import com.example.qna_backend.answer.Answer;
import com.example.qna_backend.user.domain.User;
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
        // id는 JPA가 자동으로 생성하여 DB에 저장
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

    //TODO: addAnswer 메서드 구현
    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }
}

