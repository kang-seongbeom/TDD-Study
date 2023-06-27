package com.example.tddstudy.crud.user.unit;

import com.example.tddstudy.crud.domain.Member;
import com.example.tddstudy.crud.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@TestPropertySource(locations = "classpath:application.yml")
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입")
    public void saveUser(){
        String name = "ksb";
        String password = "1234";

        Member member = Member.builder()
                .name(name)
                .password(password)
                .build();

        Member savedMember = memberRepository.save(member);

        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getPassword(), savedMember.getPassword());
    }

    @Test
    @DisplayName("회원 정보 이름으로 검색")
    public void findByNameUser(){
        String name = "ksb";
        String password = "1234";

        Member member = Member.builder()
                .name(name)
                .password(password)
                .build();

        memberRepository.save(member);

        Member findByNameMember = memberRepository.findByName("ksb").get();

        assertEquals(member.getName(), findByNameMember.getName());
        assertEquals(member.getPassword(), findByNameMember.getPassword());
    }

    @Test
    @DisplayName("로그인")
    public void login(){
        String name = "ksb";
        String password = "1234";

        Member member = Member.builder()
                .name(name)
                .password(password)
                .build();

        Member signInMember = Member.builder()
                .name(name)
                .password(password)
                .build();


        memberRepository.save(member);

        Member signInInfo = memberRepository.findByName(name).get();

        assertEquals(signInMember.getName(), signInInfo.getName());
        assertEquals(signInMember.getPassword(), signInInfo.getPassword());
    }

    @Test
    @DisplayName("회원 이름 수정")
    public void updateUser(){
        String name = "ksb";
        String password = "1234";
        String updateName = "kkk";

        Member member = Member.builder()
                .name(name)
                .password(password)
                .build();
        Member updateMember = member.copyOf();

        Member saveMember = memberRepository.save(member);
        Member saveUpdateMember = memberRepository.save(updateMember);

        // 정보 수정
        saveUpdateMember.setName(updateName); // JPA dirty checking
        memberRepository.save(saveUpdateMember);

        Member searchMember = memberRepository.findByName(saveMember.getName()).get();
        Member searchUpdateMember = memberRepository.findByName(saveUpdateMember.getName()).get();

        assertEquals(name, searchMember.getName());
        assertEquals(password, searchMember.getPassword());
        assertEquals(updateName, searchUpdateMember.getName());
        assertEquals(password, searchUpdateMember.getPassword());
    }

    @Test
    @DisplayName("회원 삭제")
    public void deleteUser(){
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        Member member = Member.builder()
                .name(name)
                .password(password)
                .build();

        memberRepository.save(member);

        memberRepository.delete(member);
        assertEquals(memberRepository.findById(id), Optional.empty());
        assertEquals(memberRepository.findByName(name), Optional.empty());
    }
}
