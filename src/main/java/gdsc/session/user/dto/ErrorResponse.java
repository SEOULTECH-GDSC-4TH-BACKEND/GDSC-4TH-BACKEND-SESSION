package gdsc.session.user.dto;


import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 * "code": "400",
 * "message": "잘못된 요청입니다."
 * }
 */

@Getter
public class ErrorResponse {

    private final HttpStatus code;
    private final String message;

    @Builder
    public ErrorResponse(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}
