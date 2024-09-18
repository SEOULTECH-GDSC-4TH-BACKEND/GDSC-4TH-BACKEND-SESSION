package gdsc.session.user.controller;


import gdsc.session.config.argumentresolver.Login;
import gdsc.session.config.interceptor.LoginCheckInterceptor;
import gdsc.session.user.dto.UserInfo;
import gdsc.session.util.SessionConst;
import gdsc.session.user.service.UserService;
import gdsc.session.user.domain.User;
import gdsc.session.user.dto.LoginRequest;
import gdsc.session.user.dto.SignupRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Validated SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return new ResponseEntity<>("가입 완료", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated LoginRequest loginRequest, HttpServletRequest request) {
        UserInfo loginUser = userService.login(loginRequest);
        setSession(request,loginUser);
        return new ResponseEntity<>("로그인 완료", HttpStatus.OK);
    }

    private void setSession(HttpServletRequest request,UserInfo loginUser) {
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
        session.setMaxInactiveInterval(1800);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        userService.logout(request);
        return new ResponseEntity<>("로그아웃 완료", HttpStatus.OK);
    }

    // 사용자의 유저 아이디 확인
    @PostMapping("/test")
    public String test(@Login UserInfo loginUser) {
        log.info("test");
        log.info("loginUser : {}", loginUser.getId());
        return "ok";
    }
}
