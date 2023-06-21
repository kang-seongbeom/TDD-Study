package com.example.tddstudy.crud.service;

import com.example.tddstudy.crud.domain.Board;
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

    public Board save(Board board) {
        return boardRepository.save(board);
    }

    public Optional<Board> findByTitle(String title) {
        return boardRepository.findByTitle(title);
    }

    public Board updateTitle(Long id, String updateTitle) {
        Board board = boardRepository.findById(id).orElseThrow();
        board.setTitle(updateTitle);
        return board;
    }

    public Board updateContent(Long id, String updateContent) {
        Board board = boardRepository.findById(id).orElseThrow();
        board.setContent(updateContent);
        return board;
    }

    public boolean delete(Long id) {
        boardRepository.deleteById(id);
        return true;
    }
}
