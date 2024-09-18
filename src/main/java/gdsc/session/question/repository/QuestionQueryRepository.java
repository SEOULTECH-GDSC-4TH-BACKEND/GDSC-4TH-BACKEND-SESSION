package gdsc.session.question.repository;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gdsc.session.question.dto.QuestionResponse;
import gdsc.session.question.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static gdsc.session.question.domain.QQuestion.question;

@Repository
@RequiredArgsConstructor
public class QuestionQueryRepository{

    private final JPAQueryFactory queryFactory;

    public Slice<QuestionResponse> getQuestionResponses(Pageable pageable) {
        List<QuestionResponse> result = queryFactory
                .query()
                .select(getQuestionResponseConstructorExpression())
                .from(question)
                .groupBy(question.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return getSlicedResponse(pageable, result);
    }

    public Slice<QuestionResponse> getQuestionResponses(
            Pageable pageable,
            SearchDto searchDto
    ) {
        List<QuestionResponse> result = queryFactory
                .query()
                .select(getQuestionResponseConstructorExpression())
                .from(question)
                .where(question.subject.like(searchDto.subject()))
                .groupBy(question.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

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
                question.author.username
        );
    }

}
