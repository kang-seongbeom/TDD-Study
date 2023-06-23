package com.example.tddstudy.crud.controller.dto;

import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.Reply;
import com.example.tddstudy.crud.domain.User;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyRequest {
    @JsonAlias("user")
    private String user;

    @JsonAlias("board")
    private String board;

    @JsonAlias("reply")
    private String reply;

    @JsonAlias
    private String update;

    public User userJsonToUser() throws JsonProcessingException {
        return new ObjectMapper().readValue(user, User.class);
    }

    public Board boardJsonToBoard() throws JsonProcessingException {
        return new ObjectMapper().readValue(board, Board.class);
    }

    public Reply replyJsonToReply() throws JsonProcessingException {
        return new ObjectMapper().readValue(reply, Reply.class);
    }
}
