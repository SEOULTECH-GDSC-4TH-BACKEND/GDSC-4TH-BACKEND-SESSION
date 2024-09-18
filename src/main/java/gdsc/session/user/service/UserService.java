package gdsc.session.user.service;


import gdsc.session.user.dto.UserInfo;
import gdsc.session.util.PasswordEncoder;
import gdsc.session.user.domain.User;
import gdsc.session.user.dto.LoginRequest;
import gdsc.session.user.dto.SignupRequest;
import gdsc.session.user.exception.AlreadyExistsEmailException;
import gdsc.session.user.exception.PasswordNotMatchException;
import gdsc.session.user.exception.UserNotFound;
import gdsc.session.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        Optional<User> userOptional = userRepository.findByEmail(signupRequest.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        if (!Objects.equals(signupRequest.getPassword1(), signupRequest.getPassword2())) {
            throw new PasswordNotMatchException();
        }

        String encryptedPassword = getEncryptedPassword(signupRequest.getEmail(),signupRequest.getPassword1());

        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(encryptedPassword)
                .username(signupRequest.getUsername())
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserInfo login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(UserNotFound::new);

        String encryptedPassword = getEncryptedPassword(loginRequest.getEmail(), loginRequest.getPassword());

        if (!Objects.equals(user.getPassword(), encryptedPassword)) {
            throw new PasswordNotMatchException();
        }

        return UserInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .build();
    }

    private String getEncryptedPassword(String email, String password) {
        return passwordEncoder.encode(email, password);
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
