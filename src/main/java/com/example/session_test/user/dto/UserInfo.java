package com.example.session_test.user.dto;

import lombok.Builder;
import lombok.Getter;

// user/dto/userInfo.java
@Getter
@Builder
public class UserInfo {

    private Long id;

    private String email;

    private String password;

    private String username;

}
