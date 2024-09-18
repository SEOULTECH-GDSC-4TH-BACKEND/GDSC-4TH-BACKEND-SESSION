package gdsc.session.answer.dto;

public record AnswerRequest(
        Long questionId,
        String content
) {
}