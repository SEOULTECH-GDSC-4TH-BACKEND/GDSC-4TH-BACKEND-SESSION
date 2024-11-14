package com.example.qna_backend.answer;

import com.example.qna_backend.question.BaseEntity;
import com.example.qna_backend.question.Question;
import com.example.qna_backend.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

