package com.example.tddstudy.crud.service;

import com.example.tddstudy.crud.domain.Member;
import com.example.tddstudy.crud.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member save(Member request) {
        return memberRepository.save(request);
    }

    public Member findByName(String name) {
        return memberRepository.findByName(name).orElseThrow();
    }

    public boolean login(Member request) {
        Member member = memberRepository.findByName(request.getName()).orElseThrow();
        return (request.getName().equals(member.getName()) && request.getPassword().equals(member.getPassword()));
    }

    public Member updateUser(Member request, String updateName) {
        Member member = memberRepository.findByName(request.getName()).orElseThrow();
        member.setName(updateName);
        return member;
    }

    public boolean delete(Member request) {
        Member member = memberRepository.findByName(request.getName()).orElseThrow();
        memberRepository.deleteById(member.getId());
        return true;
    }
}
