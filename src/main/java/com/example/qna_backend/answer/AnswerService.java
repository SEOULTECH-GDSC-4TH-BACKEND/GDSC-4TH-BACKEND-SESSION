package com.example.qna_backend.answer;

import com.example.qna_backend.question.Question;
import com.example.qna_backend.question.QuestionRepository;
import com.example.qna_backend.user.domain.User;
import com.example.qna_backend.user.dto.UserInfo;
import com.example.qna_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public Long saveAnswer(
            UserInfo userInfo,
            AnswerRequest answerRequest
    ) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        Question question = questionRepository.findById(answerRequest.questionId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_QUESTION_EXCEPTION));

        Answer answer = new Answer(answerRequest.content(), author, question);
        Answer savedAnswer = answerRepository.save(answer);

        //추가
        question.addAnswer(savedAnswer);
        return savedAnswer.getId();
    }


    public Long modifyAnswer(
            UserInfo userInfo,
            AnswerRequest answerRequest, Long answerId
    ) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_ANSWER_EXCEPTION));
        answer.update(answerRequest.content());
        return answer.getId();
    }


    public Long deleteAnswer(
            UserInfo userInfo,
            Long answerId
    ) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        return answerRepository.findById(answerId)
                .map(answer -> {
                    answerRepository.delete(answer);
                    return answer.getId();
                })
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_ANSWER_EXCEPTION));
    }
}

