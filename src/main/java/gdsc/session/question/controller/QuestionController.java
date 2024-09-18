package gdsc.session.question.controller;

import gdsc.session.config.argumentresolver.Login;
import gdsc.session.question.dto.QuestionRequest;
import gdsc.session.question.dto.QuestionResponse;
import gdsc.session.question.service.QuestionService;
import gdsc.session.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<Slice<QuestionResponse>> getQuestionPage(@PageableDefault final Pageable pageable) {
        Slice<QuestionResponse> questionPage = questionService.getQuestionPage(pageable);
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
