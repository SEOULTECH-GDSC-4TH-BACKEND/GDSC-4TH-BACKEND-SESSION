package gdsc.session.question.repository;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gdsc.session.question.dto.QuestionResponse;
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

    public Slice<QuestionResponse> questionResponses(final Pageable pageable) {
        final ConstructorExpression<QuestionResponse> questionResponse =
                Projections.constructor(
                        QuestionResponse.class,
                        question.subject,
                        question.content,
                        question.author.id,
                        question.author.username
                );

        final List<QuestionResponse> result = queryFactory
                .query()
                .select(questionResponse)
                .from(question)
                .groupBy(question.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (result.size() > pageable.getPageSize()) {
            result.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(result, pageable, hasNext);
    }

}
