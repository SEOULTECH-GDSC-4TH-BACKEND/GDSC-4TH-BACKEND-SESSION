package gdsc.session.answer.dto;

import com.querydsl.core.annotations.QueryProjection;
import gdsc.session.answer.domain.Answer;
import gdsc.session.question.domain.Question;
import lombok.Builder;

import java.util.List;

@Builder
public record AnswerResponse(
        String content,
        Long authorId,
        String author
) {
    public static List<AnswerResponse> toResponses(List<Answer> answers) {
        return answers.stream().map(answer -> AnswerResponse.builder()
                        .content(answer.getContent())
                        .authorId(answer.getAuthor().getId())
                        .author(answer.getAuthor().getUsername())
                        .build())
                .toList();
    }
}