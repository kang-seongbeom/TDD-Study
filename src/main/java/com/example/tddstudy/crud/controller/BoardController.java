package com.example.tddstudy.crud.controller;

import com.example.tddstudy.crud.controller.dto.BoardRequest;
import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/save")
    public ResponseEntity<Board> save(@RequestParam Map<String, String> params) throws JsonProcessingException {
        BoardRequest boardWriteRequest = new ObjectMapper().convertValue(params, BoardRequest.class);

        User user = boardWriteRequest.userJsonToUser();
        Board board = boardWriteRequest.boardJsonToBoard();

        Board saved = boardService.save(user, board);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saved);
    }

    @GetMapping("/find")
    public ResponseEntity<Board> findByTitle(@RequestParam String title) {
        Board saved = boardService.findByTitle(title);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saved);
    }

    @PutMapping("/updateTitle")
    public ResponseEntity<Board> updateTitle(@RequestParam Map<String, String> params) throws JsonProcessingException {
        BoardRequest boardWriteRequest = new ObjectMapper().convertValue(params, BoardRequest.class);

        User user = boardWriteRequest.userJsonToUser();
        Board board = boardWriteRequest.boardJsonToBoard();

        Board saved = boardService.updateTitle(user, board, params.get("update"));

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saved);
    }

    @PutMapping("/updateContent")
    public ResponseEntity<Board> updateContent(@RequestParam Map<String, String> params) throws JsonProcessingException {
        BoardRequest boardWriteRequest = new ObjectMapper().convertValue(params, BoardRequest.class);

        User user = boardWriteRequest.userJsonToUser();
        Board board = boardWriteRequest.boardJsonToBoard();

        Board saved = boardService.updateContent(user, board, params.get("update"));

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saved);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<String> delete(@RequestParam Map<String, String> params) throws JsonProcessingException {
        BoardRequest boardWriteRequest = new ObjectMapper().convertValue(params, BoardRequest.class);

        User user = boardWriteRequest.userJsonToUser();
        Board board = boardWriteRequest.boardJsonToBoard();

        boolean isDelete = boardService.delete(user, board);
        String response = "{isDelete:"+isDelete+"}";
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
