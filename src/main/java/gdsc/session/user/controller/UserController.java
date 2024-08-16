package gdsc.session.user.controller;


import gdsc.session.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public void signup() {

    }

    @PostMapping("/login")
    public void login() {

    }
    @PostMapping("/logout")
    public void logout() {

    }
}
