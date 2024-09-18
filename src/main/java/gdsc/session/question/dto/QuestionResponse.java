package gdsc.session.question.dto;

import com.querydsl.core.annotations.QueryProjection;
import gdsc.session.question.domain.Question;
import lombok.Builder;

@Builder
public record QuestionResponse(
        String subject,
        String content,
        Long authorId,
        String author
) {
    @QueryProjection
    public QuestionResponse(Question question){
        this(question.getSubject(),
                question.getContent(),
                question.getAuthor().getId(),
                question.getAuthor().getUsername());
    }

    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getSubject(),
                question.getContent(),
                question.getAuthor().getId(),
                question.getAuthor().getUsername()
        );
    }
}