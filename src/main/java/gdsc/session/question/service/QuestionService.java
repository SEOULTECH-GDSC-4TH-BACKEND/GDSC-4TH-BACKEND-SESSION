package gdsc.session.question.service;

import gdsc.session.question.domain.Question;
import gdsc.session.question.dto.QuestionRequest;
import gdsc.session.question.dto.QuestionResponse;
import gdsc.session.question.repository.QuestionQueryRepository;
import gdsc.session.question.repository.QuestionRepository;
import gdsc.session.user.domain.User;
import gdsc.session.user.dto.UserInfo;
import gdsc.session.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuestionQueryRepository queryRepository;
    private static int PAGE_SIZE;

    public Slice<QuestionResponse> getQuestionPage(final Pageable pageable) {
        return this.queryRepository.questionResponses(pageable);
    }

    public QuestionResponse getQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Cannot find question."));
        return QuestionResponse.builder()
                        .subject(question.getSubject())
                        .content(question.getContent())
                        .authorId(question.getAuthor().getId())
                        .author(question.getAuthor().getUsername())
                        .build();
    }

    public Long saveQuestion(
            UserInfo userInfo,
            QuestionRequest questionRequest
    ) {
        User author = userRepository.findById(userInfo.getId()).orElseThrow(() ->
                new RuntimeException("Cannot find author."));
        Question question = new Question(questionRequest.subject(), questionRequest.content(), author);
        Question savedQuestion = questionRepository.save(question);
        return savedQuestion.getId();
    }

    public Long modifyQuestion(
            UserInfo userInfo,
            QuestionRequest questionRequest, Long questionId
    ) {
        User author = userRepository.findById(userInfo.getId()).orElseThrow(() ->
                new RuntimeException("Cannot find author."));
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Cannot find question."));
        question.update(questionRequest.subject(), questionRequest.content());
        questionRepository.save(question);
        return question.getId();
    }

    public Long deleteQuestion(
            UserInfo userInfo,
            Long questionId
    ) {
        User author = userRepository.findById(userInfo.getId()).orElseThrow(() ->
                new RuntimeException("Cannot find author."));
        return questionRepository.findById(questionId)
                .map(question -> {
                    questionRepository.delete(question);
                    return question.getId();
                })
                .orElseThrow(() -> new RuntimeException("Cannot find question."));
    }
}
