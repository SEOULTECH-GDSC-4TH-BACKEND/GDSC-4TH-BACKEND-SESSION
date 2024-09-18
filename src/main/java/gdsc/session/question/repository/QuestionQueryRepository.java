package gdsc.session.question.repository;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gdsc.session.answer.dto.AnswerResponse;
import gdsc.session.question.domain.Question;
import gdsc.session.question.dto.QuestionResponse;
import gdsc.session.question.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static gdsc.session.answer.domain.QAnswer.answer;
import static gdsc.session.question.domain.QQuestion.question;
import static gdsc.session.user.domain.QUser.user;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestionQueryRepository{

    private final JPAQueryFactory queryFactory;

    public Slice<QuestionResponse> getQuestionResponses(Pageable pageable) {

//        List<QuestionResponse> result = queryFactory
//                .query()
//                .select(getQuestionResponseConstructorExpression())
//                .from(question)
//                .orderBy(question.modifiedDate.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize() + 1)
//                .fetch();

        List<Question> questions = queryFactory
                .selectFrom(question)
                .orderBy(question.modifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        List<QuestionResponse> result = questions.stream()
                .map(q -> new QuestionResponse(
                        q.getSubject(),
                        q.getSubject(),
                        q.getAuthor().getId(),
                        q.getAuthor().getUsername(),
                        AnswerResponse.toResponses(q.getAnswers()),
                        q.getModifiedDate()))
                .collect(Collectors.toList());
        return getSlicedResponse(pageable, result);
    }

    public Slice<QuestionResponse> getQuestionResponses(
            Pageable pageable,
            SearchDto searchDto
    ) {
//        List<QuestionResponse> result = queryFactory
//                .query()
//                .select(getQuestionResponseConstructorExpression())
//                .from(question)
//                .where(subjectEq(searchDto.subject()))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize() + 1)
//                .fetch();
        List<Question> questions = queryFactory
                .selectFrom(question)
                .where(question.subject.contains(searchDto.subject()))
                .orderBy(question.modifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        List<QuestionResponse> result = questions.stream()
                .map(q -> new QuestionResponse(
                        q.getSubject(),
                        q.getSubject(),
                        q.getAuthor().getId(),
                        q.getAuthor().getUsername(),
                        AnswerResponse.toResponses(q.getAnswers()),
                        q.getModifiedDate()))
                .collect(Collectors.toList());
        return getSlicedResponse(pageable, result);
    }

    private SliceImpl<QuestionResponse> getSlicedResponse(
            Pageable pageable,
            List<QuestionResponse> responses
    ) {
        int pageSize = pageable.getPageSize();
        boolean hasNext = responses.size() > pageSize;

        List<QuestionResponse> slicedResponses = hasNext ? responses.subList(0, pageSize) : responses;
        return new SliceImpl<>(slicedResponses, pageable, hasNext);
    }

    private ConstructorExpression<QuestionResponse> getQuestionResponseConstructorExpression() {
        return Projections.constructor(
                QuestionResponse.class,
                question.subject,
                question.content,
                question.author.id,
                question.author.username,
                question.answers,
                question.modifiedDate
        );
    }

}
