package com.example.qna_backend.question;

import com.example.qna_backend.answer.AnswerResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record QuestionResponse(
        String subject,
        String content,
        Long authorId,
        String author,
        List<AnswerResponse> answers, // 질문 정보와 함께 연관된 답변들도 포함
        LocalDateTime modifiedDate
) {
}
