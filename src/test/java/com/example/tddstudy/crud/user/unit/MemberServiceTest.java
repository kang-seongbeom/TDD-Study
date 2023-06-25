package com.example.tddstudy.crud.user.unit;


import com.example.tddstudy.crud.domain.Member;
import com.example.tddstudy.crud.repository.MemberRepository;
import com.example.tddstudy.crud.service.MemberService;
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
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    // @Mock 붙은 repository를 service에 주십이시기 위한 애노테이션
    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원 가입")
    public void signUp(){
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        Member request = Member.builder()
                .name(name)
                .password(password)
                .build();

        Member response = Member.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();

        given(memberRepository.save(request)).willReturn(response);

        Member result = memberService.save(request);
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

        Member request = Member.builder()
                .name(name)
                .password(password)
                .build();

        Member response = Member.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();

        given(memberRepository.save(request)).willReturn(response);
        given(memberRepository.findByName(name)).willReturn(Optional.of(response));

        memberService.save(request);
        Member result = memberService.findByName(name);

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

        Member request = Member.builder()
                .name(name)
                .password(password)
                .build();

        Member response = Member.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();

        given(memberRepository.save(request)).willReturn(response);
        given(memberRepository.findByName(name)).willReturn(Optional.of(response));

        memberService.save(request);
        boolean isLogin = memberService.login(request);

        assertTrue(isLogin);
    }

    @Test
    @DisplayName("회원 이름 수정")
    public void updateUser(){
        Long id = 1L;
        String name = "ksb";
        String password = "1234";
        String updatedName = "kkk";

        Member request = Member.builder()
                .name(name)
                .password(password)
                .build();

        Member response = Member.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();

        given(memberRepository.save(request)).willReturn(response);
        given(memberRepository.findByName(name)).willReturn(Optional.of(response));

        memberService.save(request);
        Member updateMember = memberService.updateUser(request, updatedName);

        assertEquals(updatedName, updateMember.getName());
        assertEquals(password, updateMember.getPassword());
    }

    @Test
    @DisplayName("회원 삭제")
    public void deleteUser(){
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        Member request = Member.builder()
                .name(name)
                .password(password)
                .build();

        Member response = Member.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();

        given(memberRepository.save(request)).willReturn(response);
        given(memberRepository.findByName(name)).willReturn(Optional.of(response));

        memberService.save(request);
        boolean isDelete = memberService.delete(request);

        assertTrue(isDelete);
    }
}
