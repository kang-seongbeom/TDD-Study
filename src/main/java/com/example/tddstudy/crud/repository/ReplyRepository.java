package com.example.tddstudy.crud.repository;

import com.example.tddstudy.crud.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByMemberId(Long replyMemberId);

}
