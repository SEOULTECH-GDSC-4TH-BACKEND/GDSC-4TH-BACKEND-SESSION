package gdsc.session.question.dto;

import gdsc.session.answer.dto.AnswerResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record QuestionResponse(
        String subject,
        String content,
        Long authorId,
        String author,
        List<AnswerResponse> answers,
        LocalDateTime modifiedDate
) {

}