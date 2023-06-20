package com.example.tddstudy.crud.controller;

import com.example.tddstudy.crud.controller.dto.UpdateRequest;
import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    private ResponseEntity<User> signUp(@RequestBody User user){
        User save = userService.save(user);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(save);
    }

    @GetMapping("/findbyname")
    private ResponseEntity<User> findByName(@RequestParam String name){
        User user = userService.findByName(name);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(user);
    }

    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody User user){
        boolean isLogin = userService.login(user);
        String response = "{isLogin:"+isLogin+"}";
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PutMapping("/updatename")
    private ResponseEntity<User> updateName(@RequestParam Map<String, String> params){
        UpdateRequest updateRequest = new ObjectMapper().convertValue(params, UpdateRequest.class);
        User user = User.builder().name(updateRequest.getName()).password(updateRequest.getPassword()).build();
        User update = userService.updateUser(user, updateRequest.getUpdateName());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(update);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<String> delete(@RequestBody User user){
        boolean isDelete = userService.delete(user);
        String response = "{isDelete:"+isDelete+"}";
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
