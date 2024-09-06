package com.example.session_test.repository;


import com.example.session_test.user.domain.User;
import com.example.session_test.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryUnitTest {

    @Autowired
    private UserRepository userRepository;

    private static User user;

    @BeforeEach
    void setup(){

        user = User.builder()
                .email("nmkk1234@naver.com")
                .password("1234")
                .username("kim")
                .build();
    }

    @Test
    @DisplayName("유저 생성")
    @Transactional
    public void saveUser(){
        // given
        // static user 사용

        // when
        User saveUser = userRepository.save(user);

        // then
        assertEquals(saveUser, user);
        assertEquals(saveUser.getUsername(), user.getUsername());
    }
    @Test
    @DisplayName("이메일로 유저 조회")
    @Transactional
    public void findByEmail(){
        // given
        userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());

        // then
        assertNotNull(findUser);
        assertEquals(findUser.get().getUsername(), "kim");
    }
    @Test
    @DisplayName("유저 삭제")
    @Transactional
    public void deleteUser(){
        // given
        userRepository.save(user);

        // when
        userRepository.delete(user);

        // then
        List<User> findUsers = userRepository.findAll();
        assertEquals(findUsers.size(), 0);
    }
}
