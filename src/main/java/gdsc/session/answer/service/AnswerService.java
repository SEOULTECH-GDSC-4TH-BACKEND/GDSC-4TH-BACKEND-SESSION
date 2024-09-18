package gdsc.session.answer.service;

import gdsc.session.answer.domain.Answer;
import gdsc.session.answer.dto.AnswerRequest;
import gdsc.session.answer.repository.AnswerRepository;
import gdsc.session.config.argumentresolver.Login;
import gdsc.session.user.domain.User;
import gdsc.session.user.dto.UserInfo;
import gdsc.session.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    public Long saveAnswer(
            UserInfo userInfo,
            AnswerRequest answerRequest
    ) {
        User author = userRepository.findById(userInfo.getId()).orElseThrow(() ->
                new RuntimeException("Cannot find author."));
        Answer answer = new Answer(answerRequest.content(), author);
        Answer savedAnswer = answerRepository.save(answer);
        return savedAnswer.getId();
    }

    public Long modifyAnswer(
            UserInfo userInfo,
            AnswerRequest answerRequest, Long answerId
    ) {
        User author = userRepository.findById(userInfo.getId()).orElseThrow(() ->
                new RuntimeException("Cannot find author."));
        Answer updatedAnswer = answerRepository.findById(answerId).orElseThrow(() -> new RuntimeException("Cannot find answer."));
        updatedAnswer.update(answerRequest.content());
        return updatedAnswer.getId();
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
