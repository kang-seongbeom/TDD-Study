package com.example.tddstudy.crud.repository;

import com.example.tddstudy.crud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
