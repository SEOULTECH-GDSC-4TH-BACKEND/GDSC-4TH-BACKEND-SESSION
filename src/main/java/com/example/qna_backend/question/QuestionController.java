package com.example.qna_backend.question;

import com.example.qna_backend.config.argumentresolver.Login;
import com.example.qna_backend.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<Slice<QuestionResponse>> getQuestionPage(
            @PageableDefault final Pageable pageable,
            @Validated @ModelAttribute SearchDto searchDto
    ) {
        Slice<QuestionResponse> questionPage = questionService.getQuestionPage(pageable, searchDto);
        return ResponseEntity.ok(questionPage);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> getQuestion(@PathVariable Long questionId) {
        QuestionResponse response = questionService.getQuestion(questionId);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Long> createQuestion(
            @Login UserInfo userInfo,
            @RequestBody QuestionRequest questionRequest
    ) {
        Long questionId = questionService.saveQuestion(userInfo, questionRequest);
        return ResponseEntity.ok(questionId);
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<Long> modifyQuestion(
            @Login UserInfo userInfo,
            @PathVariable Long questionId,
            @RequestBody QuestionRequest questionRequest
    ) {
        Long updatedQuestionId = questionService.modifyQuestion(userInfo, questionRequest, questionId);
        return ResponseEntity.ok(updatedQuestionId);
    }


    @DeleteMapping("/{questionId}")
    public ResponseEntity<Long> deleteQuestion(
            @Login UserInfo userInfo,
            @PathVariable Long questionId
    ) {
        Long deletedQuestionId = questionService.deleteQuestion(userInfo, questionId);
        return ResponseEntity.ok(deletedQuestionId);
    }
}

