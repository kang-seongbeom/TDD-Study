package com.example.tddstudy.crud.repository;

import com.example.tddstudy.crud.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByTitle(String title);

    @Query
    List<Board> findByMemberId(Long id);
}
