package com.example.qna_backend.question;

import com.example.qna_backend.answer.Answer;
import com.example.qna_backend.answer.AnswerResponse;
import com.example.qna_backend.answer.BusinessException;
import com.example.qna_backend.answer.ErrorCode;
import com.example.qna_backend.user.domain.User;
import com.example.qna_backend.user.dto.UserInfo;
import com.example.qna_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    // TODO : 페이징

    // 1. 단일 질문 조회
    public QuestionResponse getQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE_EXCEPTION));

        List<Answer> answers = question.getAnswers();
        List<AnswerResponse> answerResponses = AnswerResponse.toResponses(answers);

        return QuestionResponse.builder()
                .subject(question.getSubject())
                .content(question.getContent())
                .authorId(question.getAuthor().getId())
                .author(question.getAuthor().getUsername())
                .answers(answerResponses)
                .modifiedDate(question.getModifiedDate())
                .build();
    }


    // 2. 질문 저장
    public Long saveQuestion(
            UserInfo userInfo,
            QuestionRequest questionRequest
    ) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Question question = new Question(questionRequest.subject(), questionRequest.content(), author);
        Question savedQuestion = questionRepository.save(question);
        return savedQuestion.getId();
    }


    // 3. 질문 수정
    public Long modifyQuestion(
            UserInfo userInfo,
            QuestionRequest questionRequest, Long questionId
    ) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 질문입니다."));
        // update 메소드 작성
        question.update(questionRequest.subject(), questionRequest.content());
        questionRepository.save(question);
        return question.getId();
    }


    // 4. 질문 삭제
    public Long deleteQuestion(
            UserInfo userInfo,
            Long questionId
    ) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        return questionRepository.findById(questionId)
                .map(question -> {
                    questionRepository.delete(question);
                    return question.getId();
                })
                .orElseThrow(() -> new RuntimeException("존재하지 않는 질문입니다."));
    }
}

