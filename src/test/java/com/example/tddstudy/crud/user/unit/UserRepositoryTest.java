package com.example.tddstudy.crud.user.unit;

import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.yml")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("회원 가입")
    public void saveUser(){
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        User user = User.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();

        testEntityManager.persist(user);
        User savedUser = userRepository.save(user);

        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    @DisplayName("회원 정보 이름으로 검색")
    public void findByNameUser(){
        String name = "ksb";
        String password = "1234";

        User user = User.builder()
                .name(name)
                .password(password)
                .build();

        given(userRepository.save(user)).willReturn(user);

        User saveUser = userRepository.save(user);

        given(userRepository.findByName(name)).willReturn(Optional.of(saveUser));

        User findByNameUser = userRepository.findByName("ksb").get();

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

        given(userRepository.findByName(saveUser.getName())).willReturn(Optional.of(user));

        User signInInfo = userRepository.findByName(name).get();

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
        userRepository.save(saveUpdateUser);

        // 수정된 정보를 바탕으로 검색
        given(userRepository.findByName(saveUser.getName())).willReturn(Optional.of(saveUser));
        given(userRepository.findByName(saveUpdateUser.getName())).willReturn(Optional.of(saveUpdateUser));

        User searchUser = userRepository.findByName(saveUser.getName()).get();
        User searchUpdateUser = userRepository.findByName(saveUpdateUser.getName()).get();

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
