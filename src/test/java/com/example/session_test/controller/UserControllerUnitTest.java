package com.example.session_test.controller;


import com.example.session_test.user.domain.User;
import com.example.session_test.user.dto.LoginRequest;
import com.example.session_test.user.dto.SignupRequest;
import com.example.session_test.user.dto.UserInfo;
import com.example.session_test.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class UserControllerUnitTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("유저 회원가입 요청")
    void signup() throws Exception {
        SignupRequest signup = SignupRequest
                .builder()
                .email("nmkk1234@naver.com")
                .password1("1234")
                .password2("1234")
                .username("kim")
                .build();

        doNothing().when(userService).signup(signup);

        mockMvc.perform(post("/api/user/signup")
                        .content(objectMapper.writeValueAsString(signup))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("가입 완료"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("유저 로그인 요청")
    void login() throws Exception{
        LoginRequest user = LoginRequest.builder()
                .email("nmkk1234@naver.com")
                .password("1234")
                .build();

        when(userService.login(user)).thenReturn(UserInfo.builder()
                .email("nmkk1234@naver.com")
                .password("1234")
                .id(1L)
                .username("kim")
                .build());

        mockMvc.perform(post("/api/user/login")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("로그인 완료"))
                .andDo(MockMvcResultHandlers.print());
    }


}
