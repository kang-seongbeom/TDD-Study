package com.example.tddstudy.crud.service;

import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User request) {
        return userRepository.save(request);
    }

    public User findByName(String name) {
        return userRepository.findByName(name).orElseThrow();
    }

    public boolean login(User request) {
        User user = userRepository.findByName(request.getName()).orElseThrow();
        return (request.getName().equals(user.getName()) && request.getPassword().equals(user.getPassword()));
    }

    public User updateUser(User request, String updateName) {
        User user = userRepository.findByName(request.getName()).orElseThrow();
        user.setName(updateName);
        return user;
    }

    public boolean delete(User request) {
        User user = userRepository.findByName(request.getName()).orElseThrow();
        userRepository.deleteById(user.getId());
        return true;
    }
}
