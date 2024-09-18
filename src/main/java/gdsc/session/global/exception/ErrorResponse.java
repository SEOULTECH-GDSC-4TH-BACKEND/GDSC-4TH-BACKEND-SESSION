package gdsc.session.global.exception;


import org.springframework.http.ResponseEntity;

public record ErrorResponse(String code, String message) {
    public static ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttp)
    }
}
