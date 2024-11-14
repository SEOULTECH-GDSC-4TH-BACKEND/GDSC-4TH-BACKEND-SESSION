package com.example.qna_backend.answer;

import lombok.Builder;

import java.util.List;

// AnswerResponse.java

@Builder
public record AnswerResponse(
        String content,
        Long authorId,
        String author
) {
    // 엔티티 리스트를 DTO 리스트로 변환
    public static List<AnswerResponse> toResponses(List<Answer> answers) {
        return answers.stream().map(answer -> AnswerResponse.builder()
                        .content(answer.getContent())
                        .authorId(answer.getAuthor().getId())
                        .author(answer.getAuthor().getUsername())
                        .build())
                .toList();
    }
}
