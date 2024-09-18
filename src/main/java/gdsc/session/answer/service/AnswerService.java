package gdsc.session.answer.service;

import gdsc.session.answer.domain.Answer;
import gdsc.session.answer.dto.AnswerRequest;
import gdsc.session.answer.repository.AnswerRepository;
import gdsc.session.question.domain.Question;
import gdsc.session.question.repository.QuestionRepository;
import gdsc.session.user.domain.User;
import gdsc.session.user.dto.UserInfo;
import gdsc.session.user.repository.UserRepository;
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
                .orElseThrow(() -> new RuntimeException("Cannot find author."));
        Question question = questionRepository.findById(answerRequest.questionId())
                .orElseThrow(() -> new RuntimeException("Cannot find question."));
        Answer answer = new Answer(answerRequest.content(), author, question);
        Answer savedAnswer = answerRepository.save(answer);
        question.addAnswer(savedAnswer);
        return savedAnswer.getId();
    }

    public Long modifyAnswer(
            UserInfo userInfo,
            AnswerRequest answerRequest, Long answerId
    ) {
        User author = userRepository.findById(userInfo.getId()).orElseThrow(() ->
                new RuntimeException("Cannot find author."));
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new RuntimeException("Cannot find answer."));
        answer.update(answerRequest.content());
        return answer.getId();
    }

    public Long deleteAnswer(
            UserInfo userInfo,
            Long answerId
    ) {
        User author = userRepository.findById(userInfo.getId()).orElseThrow(() ->
                new RuntimeException("Cannot find author."));
        return answerRepository.findById(answerId)
                .map(answer -> {
                    answerRepository.delete(answer);
                    return answer.getId();
                })
                .orElseThrow(() -> new RuntimeException("Cannot find answer."));
    }
}
