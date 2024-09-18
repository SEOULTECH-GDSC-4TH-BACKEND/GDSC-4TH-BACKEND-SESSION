package gdsc.session.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /**
     * common. code prefix: common-
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "common-1", "서버 에러가 발생했습니다"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "common-2", "입력값이 올바르지 않습니다."),
    NOT_FOUND_RESOURCE_EXCEPTION(HttpStatus.NOT_FOUND, "common-3", "존재하지 않는 데이터입니다."),
    DUPLICATED_RESOURCE_EXCEPTION(HttpStatus.CONFLICT, "common-4", "이미 존재하는 데이터입니다."),
    PARSE_JSON_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "common-5", "JSON 파싱 에러가 발생했습니다."),
    NOT_FOUND_HANDLER_EXCEPTION(HttpStatus.NOT_FOUND, "common-8", "지원하지 않는 Api 요청 입니다."),
    NOT_FOUND_USER_EXCEPTION(HttpStatus.NOT_FOUND, "common-9", "존재하지 않는 사용자입니다."),
    INVALID_ACCESS_EXCEPTION(HttpStatus.FORBIDDEN, "common-10", "잘못된 접근입니다."),

    /**
     * auth. code prefix: auth-
     */
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "auth-1", "인증되지 않은 사용자입니다."),

    /**
     * resource. code prefix: resource-
     */
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, "resource-1", "리소스에 접근 권한이 없습니다."),

    /**
     * matching. code prefix: matching-
     */

    NOT_EXIST_QUESTION_EXCEPTION(HttpStatus.NOT_FOUND, "question-1", "질문이 존재하지 않습니다."),
    NOT_EXIST_ANSWER_EXCEPTION(HttpStatus.NOT_FOUND, "answer-1", "답변이 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}