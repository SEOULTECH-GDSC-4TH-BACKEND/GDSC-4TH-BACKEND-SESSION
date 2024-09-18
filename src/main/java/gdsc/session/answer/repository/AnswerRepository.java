package gdsc.session.answer.repository;


import gdsc.session.answer.domain.Answer;
import gdsc.session.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
