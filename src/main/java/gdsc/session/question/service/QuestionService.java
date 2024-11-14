package gdsc.session.question.service;

import gdsc.session.answer.domain.Answer;
import gdsc.session.answer.dto.AnswerResponse;
import gdsc.session.global.exception.BusinessException;
import gdsc.session.global.exception.ErrorCode;
import gdsc.session.question.domain.Question;
import gdsc.session.question.dto.QuestionRequest;
import gdsc.session.question.dto.QuestionResponse;
import gdsc.session.question.dto.SearchDto;
import gdsc.session.question.repository.QuestionQueryRepository;
import gdsc.session.question.repository.QuestionRepository;
import gdsc.session.user.domain.User;
import gdsc.session.user.dto.UserInfo;
import gdsc.session.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuestionQueryRepository queryRepository;

    @Transactional
    public Slice<QuestionResponse> getQuestionPage(Pageable pageable, SearchDto searchDto) {
        if (Objects.isNull(searchDto.subject())) {
            return queryRepository.getQuestionResponses(pageable);
        } else {
            return queryRepository.getQuestionResponses(pageable, searchDto);
        }
    }

    public QuestionResponse getQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

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

    public Long saveQuestion(
            UserInfo userInfo,
            QuestionRequest questionRequest
    ) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        Question question = new Question(questionRequest.subject(), questionRequest.content(), author);
        Question savedQuestion = questionRepository.save(question);
        return savedQuestion.getId();
    }

    public Long modifyQuestion(
            UserInfo userInfo,
            QuestionRequest questionRequest, Long questionId
    ) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_QUESTION_EXCEPTION));
        question.update(questionRequest.subject(), questionRequest.content());
        questionRepository.save(question);
        return question.getId();
    }

    public Long deleteQuestion(
            UserInfo userInfo,
            Long questionId
    ) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        return questionRepository.findById(questionId)
                .map(question -> {
                    questionRepository.delete(question);
                    return question.getId();
                })
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_QUESTION_EXCEPTION));
    }
}
