package com.example.tddstudy.crud.controller;

import com.example.tddstudy.crud.controller.dto.MemberRequest;
import com.example.tddstudy.crud.domain.Member;
import com.example.tddstudy.crud.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/signup")
    private ResponseEntity<Member> signUp(@RequestBody Member member){
        Member save = memberService.save(member);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(save);
    }

    @GetMapping("/findbyname")
    private ResponseEntity<Member> findByName(@RequestParam String name){
        Member member = memberService.findByName(name);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(member);
    }

    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody Member member){
        boolean isLogin = memberService.login(member);
        String response = "{isLogin:"+isLogin+"}";
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PutMapping("/updatename")
    private ResponseEntity<Member> updateName(@RequestParam Map<String, String> params){
        MemberRequest memberRequest = new ObjectMapper().convertValue(params, MemberRequest.class);
        Member member = Member.builder().name(memberRequest.getName()).password(memberRequest.getPassword()).build();
        Member update = memberService.updateUser(member, memberRequest.getUpdateName());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(update);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<String> delete(@RequestBody Member member){
        boolean isDelete = memberService.delete(member);
        String response = "{isDelete:"+isDelete+"}";
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
