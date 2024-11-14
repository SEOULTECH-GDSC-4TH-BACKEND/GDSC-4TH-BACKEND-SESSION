package com.example.qna_backend.question;

public record QuestionRequest(
        String subject,
        String content
) {
}
