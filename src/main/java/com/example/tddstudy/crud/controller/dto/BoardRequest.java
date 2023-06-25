package com.example.tddstudy.crud.controller.dto;

import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.Member;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequest {
    @JsonAlias("member")
    private String member;

    @JsonAlias("board")
    private String board;

    @JsonAlias
    private String update;

    public Member memberJsonToUser() throws JsonProcessingException {
        return new ObjectMapper().readValue(member, Member.class);
    }

    public Board boardJsonToBoard() throws JsonProcessingException {
        return new ObjectMapper().readValue(board, Board.class);
    }

}
