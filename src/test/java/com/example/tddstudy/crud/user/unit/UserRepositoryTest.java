package com.example.tddstudy.crud.user.unit;

import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

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

        given(userRepository.save(user)).willReturn(user);

        User saveUser = userRepository.save(user);

        given(userRepository.findByName(name)).willReturn(saveUser);

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

        given(userRepository.save(user)).willReturn(user);
        User saveUser = userRepository.save(user);

        given(userRepository.findByName(saveUser.getName())).willReturn(user);

        User signInInfo = userRepository.findByName(name);

        assertEquals(signInUser.getName(), signInInfo.getName());
        assertEquals(signInUser.getPassword(), signInInfo.getPassword());
    }

    @Test
    @DisplayName("회원 이름 수정")
    public void updateUser(){
        String name = "ksb";
        String password = "1234";
        String updateName = "kkk";

        User user = User.builder()
                .name(name)
                .password(password)
                .build();
        User updateUser = user.copyOf();

        // 회원 저장
        given(userRepository.save(user)).willReturn(user);
        given(userRepository.save(updateUser)).willReturn(updateUser);

        User saveUser = userRepository.save(user);
        User saveUpdateUser = userRepository.save(updateUser);

        // 정보 수정
        saveUpdateUser.setName(updateName); // JPA dirty checking

        // 수정된 정보를 바탕으로 검색
        given(userRepository.findByName(saveUser.getName())).willReturn(saveUser);
        given(userRepository.findByName(saveUpdateUser.getName())).willReturn(saveUpdateUser);

        User searchUser = userRepository.findByName(saveUser.getName());
        User searchUpdateUser = userRepository.findByName(saveUpdateUser.getName());

        assertEquals(name, searchUser.getName());
        assertEquals(password, searchUser.getPassword());
        assertEquals(updateName, searchUpdateUser.getName());
        assertEquals(password, searchUpdateUser.getPassword());
    }

    @Test
    @DisplayName("회원 삭제")
    public void deleteUser(){
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        User user = User.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();

        given(userRepository.save(user)).willReturn(user);

        User saveUser = userRepository.save(user);

        doNothing().when(userRepository).deleteById(saveUser.getId());
        given(userRepository.findById(id)).willReturn(null);
        given(userRepository.findByName(name)).willReturn(null);

        userRepository.deleteById(id);
        assertNull(userRepository.findById(id));
        assertNull(userRepository.findByName(name));
    }
}
