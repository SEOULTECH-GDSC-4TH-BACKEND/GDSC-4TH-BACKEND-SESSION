package com.example.qna_backend.answer;

public record AnswerRequest(
        Long questionId,
        String content
) {
}
