package com.example.tddstudy.crud.repository;

import com.example.tddstudy.crud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
