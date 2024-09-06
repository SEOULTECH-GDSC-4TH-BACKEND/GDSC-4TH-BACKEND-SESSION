package com.example.session_test.service;


import com.example.session_test.user.domain.User;
import com.example.session_test.user.dto.LoginRequest;
import com.example.session_test.user.dto.SignupRequest;
import com.example.session_test.user.dto.UserInfo;
import com.example.session_test.user.repository.UserRepository;
import com.example.session_test.user.service.UserService;
import com.example.session_test.util.PasswordEncoder;
import org.assertj.core.api.BDDAssumptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("회원가입 성공")
    public void signupSuccess() {
        // Given
        SignupRequest signupRequest = SignupRequest.builder()
                .email("nmkk1234@naver.com")
                .username("kim")
                .password1("123")
                .password2("123")
                .build();

        userService.signup(signupRequest);

        // Expected
        Assertions.assertDoesNotThrow(() -> userService.signup(signupRequest));
    }

    @Test
    @DisplayName("로그인 성공")
    public void loginSuccess() {
        // Given
        User user = User.builder().email("nmkk1234@naver.com")
                .username("kim")
                .password("123")
                .build();
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        given(passwordEncoder.encode(anyString(),anyString())).willReturn(user.getPassword());

        // when
        LoginRequest loginRequest = LoginRequest.builder()
                .email("nmkk1234@naver.com")
                .password("123")
                .build();
        UserInfo userInfo = userService.login(loginRequest);
        // then
        assertNotNull(userInfo);
        assertEquals(userInfo.getEmail(), loginRequest.getEmail());
    }
}
