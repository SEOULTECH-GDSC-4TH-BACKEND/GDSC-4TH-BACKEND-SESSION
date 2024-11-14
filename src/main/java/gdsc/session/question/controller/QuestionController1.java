package gdsc.session.question.controller;

import gdsc.session.global.config.argumentresolver.Login;
import gdsc.session.question.dto.QuestionRequest;
import gdsc.session.question.service.QuestionService;
import gdsc.session.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController1 {
    private final QuestionService questionService;

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
