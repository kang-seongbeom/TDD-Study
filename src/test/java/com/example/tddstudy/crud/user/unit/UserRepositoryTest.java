package com.example.tddstudy.crud.user.unit;

import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class) // mock 애노테이션 사용시, mockito 테스트 실행 확장을 위해 필요
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 정보 DB 저장")
    public void saveUserInDB(){
        String name = "ksb";
        String password = "1234";

        User user = User.builder()
                .name(name)
                .password(password)
                .build();

        given(userRepository.save(any())).willReturn(user);

        User savedUser = userRepository.save(user);

        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    @DisplayName("회원 정보 이름으로 검색")
    public void findByNameUserInDB(){
        String name = "ksb";
        String password = "1234";

        User user = User.builder()
                .name(name)
                .password(password)
                .build();

        given(userRepository.findByName(name)).willReturn(user);

        User findByNameUser = userRepository.findByName("ksb");

        assertEquals(user.getName(), findByNameUser.getName());
        assertEquals(user.getPassword(), findByNameUser.getPassword());
    }

    @Test
    @DisplayName("로그인")
    public void login(){
        String name = "ksb";
        String password = "1234";

        User user = User.builder()
                .name(name)
                .password(password)
                .build();

        User signInUser = User.builder()
                .name(name)
                .password(password)
                .build();

        given(userRepository.findByName(name)).willReturn(user);

        User signInInfo = userRepository.findByName(name);

        assertEquals(signInUser.getName(), signInInfo.getName());
        assertEquals(signInUser.getPassword(), signInInfo.getPassword());
    }
}
