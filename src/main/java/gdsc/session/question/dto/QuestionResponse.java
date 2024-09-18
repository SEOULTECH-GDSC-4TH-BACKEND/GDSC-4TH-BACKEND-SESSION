package gdsc.session.question.dto;

import com.querydsl.core.annotations.QueryProjection;
import gdsc.session.answer.dto.AnswerResponse;
import gdsc.session.question.domain.Question;
import lombok.Builder;

import java.util.List;

@Builder
public record QuestionResponse(
        String subject,
        String content,
        Long authorId,
        String author,
        List<AnswerResponse> answers
) {

}