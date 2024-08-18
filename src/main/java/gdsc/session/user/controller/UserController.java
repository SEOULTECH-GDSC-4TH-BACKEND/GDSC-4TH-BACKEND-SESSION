package gdsc.session.user.controller;


import gdsc.session.util.SessionConst;
import gdsc.session.user.service.UserService;
import gdsc.session.user.domain.User;
import gdsc.session.user.dto.LoginRequest;
import gdsc.session.user.dto.SignupRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody @Validated SignupRequest signupRequest) {
        userService.signup(signupRequest);
    }

    @PostMapping("/login")
    public void login(@RequestBody @Validated LoginRequest loginRequest, HttpServletRequest request) {
        User loginUser = userService.login(loginRequest);

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
        session.setMaxInactiveInterval(1800);
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        userService.logout(request);
    }

    @PostMapping("/test")
    public String test() {
        log.info("test");
        return "ok";
    }
}
