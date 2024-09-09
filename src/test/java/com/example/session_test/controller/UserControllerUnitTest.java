package com.example.session_test.controller;


import com.example.session_test.user.controller.UserController;
import com.example.session_test.user.domain.User;
import com.example.session_test.user.dto.LoginRequest;
import com.example.session_test.user.dto.UserInfo;
import com.example.session_test.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerUnitTest {

    @Autowired private ObjectMapper objectMapper;
    @Autowired private MockMvc mockMvc;
    @MockBean private UserService userService;

    @Test
    @DisplayName("회원가입")
    void signup() {
        // 회원가입은 서비스 코드에서 테스트한다.
    }

    @Test
    @DisplayName("유저 로그인 요청")
    void login() throws Exception{
        // given
        LoginRequest user = LoginRequest.builder()
                .email("nmkk1234@naver.com")
                .password("1234")
                .build();

        // when
        when(userService.login(user)).thenReturn(UserInfo.builder()
                .email("nmkk1234@naver.com")
                .password("1234")
                .id(1L)
                .username("kim")
                .build());

        // then
        mockMvc.perform(post("/api/user/login")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("로그인 완료"))
                .andDo(MockMvcResultHandlers.print());
    }
}
