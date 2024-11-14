package gdsc.session.question.service;

import gdsc.session.question.domain.Question;
import gdsc.session.question.dto.QuestionRequest;
import gdsc.session.question.repository.QuestionRepository;
import gdsc.session.user.domain.User;
import gdsc.session.user.dto.UserInfo;
import gdsc.session.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService1 {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

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

    public Long modifyQuestion(
            UserInfo userInfo,
            QuestionRequest questionRequest,
            Long questionId
    ) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 질문입니다."));
        question.update(questionRequest.subject(), questionRequest.content());
        questionRepository.save(question);
        return question.getId();
    }

    public Long deleteQuestion(
            UserInfo userInfo,
            Long questionId
    ) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        return questionRepository.findById(questionId)
                .map(question -> {
                    questionRepository.delete(question);
                    return question.getId();
                })
                .orElseThrow(() -> new RuntimeException("존재하지 않는 질문입니다."));
    }
}




















