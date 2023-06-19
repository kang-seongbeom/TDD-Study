package com.example.tddstudy.crud.user.unit;


import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.repository.UserRepository;
import com.example.tddstudy.crud.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    // @Mock 붙은 repository를 service에 주십이시기 위한 애노테이션
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원 가입")
    public void signUp(){
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        User request = User.builder()
                .name(name)
                .password(password)
                .build();

        User response = User.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();

        given(userRepository.save(request)).willReturn(response);

        User result = userService.save(request);
        assertEquals(result.getId(), id);
        assertEquals(result.getName(), name);
        assertEquals(result.getPassword(), password);
    }

    @Test
    @DisplayName("회원 정보 이름으로 검색")
    public void findByNameUserInDB(){
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        User request = User.builder()
                .name(name)
                .password(password)
                .build();

        User response = User.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();

        given(userRepository.save(request)).willReturn(response);
        given(userRepository.findByName(name)).willReturn(Optional.of(response));

        userService.save(request);
        User result = userService.findByName(name);

        assertEquals(result.getId(), response.getId());
        assertEquals(result.getName(), response.getName());
        assertEquals(result.getPassword(), response.getPassword());
    }

    @Test
    @DisplayName("로그인")
    public void login(){
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        User request = User.builder()
                .name(name)
                .password(password)
                .build();

        User response = User.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();

        given(userRepository.save(request)).willReturn(response);
        given(userRepository.findByName(name)).willReturn(Optional.of(response));

        userService.save(request);
        boolean isLogin = userService.login(request);

        assertTrue(isLogin);
    }

    @Test
    @DisplayName("회원 이름 수정")
    public void updateUser(){
        Long id = 1L;
        String name = "ksb";
        String password = "1234";
        String updatedName = "kkk";

        User request = User.builder()
                .name(name)
                .password(password)
                .build();

        User response = User.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();

        given(userRepository.save(request)).willReturn(response);
        given(userRepository.findByName(name)).willReturn(Optional.of(response));

        userService.save(request);
        User updateUser = userService.updateUser(request, updatedName);

        assertEquals(updatedName, updateUser.getName());
        assertEquals(password, updateUser.getPassword());
    }

    @Test
    @DisplayName("회원 삭제")
    public void deleteUser(){
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        User request = User.builder()
                .name(name)
                .password(password)
                .build();

        User response = User.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();

        given(userRepository.save(request)).willReturn(response);
        given(userRepository.findByName(name)).willReturn(Optional.of(response));

        userService.save(request);
        boolean isDelete = userService.delete(request);

        assertTrue(isDelete);
    }
}
