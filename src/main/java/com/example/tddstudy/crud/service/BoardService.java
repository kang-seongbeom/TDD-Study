package com.example.tddstudy.crud.service;

import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Board save(User user, Board board) {
        board.setUser(user);
        return boardRepository.save(board);
    }

    public Board findByTitle(String title) {
        return boardRepository.findByTitle(title).orElseThrow();
    }

    public Board updateTitle(User user, Board board, String updateTitle) {
        Board findBoard = boardRepository.findById(board.getId()).orElseThrow();
        if(findBoard.getUser().getId().equals(user.getId())) {
            findBoard.setTitle(updateTitle);
        }
        return board;
    }

    public Board updateContent(User user, Board board, String updateContent) {
        Board findBoard = boardRepository.findById(board.getId()).orElseThrow();
        if(findBoard.getUser().getId().equals(user.getId())) {
            findBoard.setTitle(updateContent);
        }
        return board;
    }

    public boolean delete(User user, Board board) {
        Board findBoard = boardRepository.findById(board.getId()).orElseThrow();
        if(findBoard.getUser().getId().equals(user.getId())) {
            boardRepository.deleteById(board.getId());
        }
        return true;
    }
}
