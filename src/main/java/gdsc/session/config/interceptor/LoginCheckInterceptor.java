package gdsc.session.config.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc.session.util.SessionConst;
import gdsc.session.user.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("인터셉터 실행");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER)
                == null) {
            sendErrorResponse(response, "401", "로그인하지 않은 사용자");
            return false;
        }
        return true;
    }

    private void sendErrorResponse(HttpServletResponse response, String code, String message) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(code)
                .message(message)
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(json);
    }
}
