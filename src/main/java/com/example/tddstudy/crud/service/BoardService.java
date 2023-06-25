package com.example.tddstudy.crud.service;

import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.Member;
import com.example.tddstudy.crud.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Board save(Member member, Board board) {
        board.setMember(member);
        return boardRepository.save(board);
    }

    public Board findByTitle(String title) {
        return boardRepository.findByTitle(title).orElseThrow();
    }

    public Board updateTitle(Member member, Board board, String updateTitle) {
        Board findBoard = boardRepository.findById(board.getId()).orElseThrow();
        if(findBoard.getMember().getId().equals(member.getId())) {
            findBoard.setTitle(updateTitle);
        }
        return board;
    }

    public Board updateContent(Member member, Board board, String updateContent) {
        Board findBoard = boardRepository.findById(board.getId()).orElseThrow();
        if(findBoard.getMember().getId().equals(member.getId())) {
            findBoard.setTitle(updateContent);
        }
        return board;
    }

    public boolean delete(Member member, Board board) {
        Board findBoard = boardRepository.findById(board.getId()).orElseThrow();
        if(findBoard.getMember().getId().equals(member.getId())) {
            boardRepository.deleteById(board.getId());
        }
        return true;
    }
}
