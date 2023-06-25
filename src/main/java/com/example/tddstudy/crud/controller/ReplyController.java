package com.example.tddstudy.crud.controller;

import com.example.tddstudy.crud.controller.dto.ReplyRequest;
import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.Reply;
import com.example.tddstudy.crud.domain.Member;
import com.example.tddstudy.crud.service.ReplyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PostMapping("/save")
    public ResponseEntity<Reply> save(@RequestParam Map<String, String> params) throws JsonProcessingException {
        ReplyRequest replyRequest = new ObjectMapper().convertValue(params, ReplyRequest.class);

        Member member = replyRequest.memberJsonToUser();
        Board board = replyRequest.boardJsonToBoard();
        Reply reply = replyRequest.replyJsonToReply();

        Reply saved = replyService.save(member, board, reply);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saved);
    }

    @GetMapping("/findbyuserid")
    public ResponseEntity<List<Reply>> findByUserId(@RequestParam Map<String, String> params) throws JsonProcessingException {
        ReplyRequest replyRequest = new ObjectMapper().convertValue(params, ReplyRequest.class);

        Member member = replyRequest.memberJsonToUser();

        List<Reply> saved = replyService.findByUserId(member);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saved);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestParam Map<String, String> params) throws JsonProcessingException {
        ReplyRequest replyRequest = new ObjectMapper().convertValue(params, ReplyRequest.class);

        Member member = replyRequest.memberJsonToUser();
        Reply reply = replyRequest.replyJsonToReply();
        String update = replyRequest.getUpdate();

        boolean isUpdate = replyService.updateReplyContent(member, reply, update);

        String response = "{isUpdate:"+isUpdate+"}";
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Map<String, String> params) throws JsonProcessingException {
        ReplyRequest replyRequest = new ObjectMapper().convertValue(params, ReplyRequest.class);

        Member member = replyRequest.memberJsonToUser();
        Board board = replyRequest.boardJsonToBoard();
        Reply reply = replyRequest.replyJsonToReply();

        boolean isDelete = replyService.delete(member, board, reply);

        String response = "{isDelete:"+isDelete+"}";
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
