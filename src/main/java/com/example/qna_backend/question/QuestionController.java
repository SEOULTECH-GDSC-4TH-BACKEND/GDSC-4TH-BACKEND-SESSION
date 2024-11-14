package com.example.qna_backend.question;

import com.example.qna_backend.config.argumentresolver.Login;
import com.example.qna_backend.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    // TODO : 페이지

    // 1. 단일 질문 조회
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> getQuestion(@PathVariable Long questionId) {
        QuestionResponse response = questionService.getQuestion(questionId);
        return ResponseEntity.ok(response);
    }


    // 2. 질문 등록
    @PostMapping
    public ResponseEntity<Long> createQuestion(
            @Login UserInfo userInfo,
            @RequestBody QuestionRequest questionRequest
    ) {
        Long questionId = questionService.saveQuestion(userInfo, questionRequest);
        return ResponseEntity.ok(questionId);
    }


    // 3. 질문 수정
    @PatchMapping("/{questionId}")
    public ResponseEntity<Long> modifyQuestion(
            @Login UserInfo userInfo,
            @PathVariable Long questionId,
            @RequestBody QuestionRequest questionRequest
    ) {
        Long updatedQuestionId = questionService.modifyQuestion(userInfo, questionRequest, questionId);
        return ResponseEntity.ok(updatedQuestionId);
    }


    // 4. 질문 삭제
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Long> deleteQuestion(
            @Login UserInfo userInfo,
            @PathVariable Long questionId
    ) {
        Long deletedQuestionId = questionService.deleteQuestion(userInfo, questionId);
        return ResponseEntity.ok(deletedQuestionId);
    }
}

