package com.example.tddstudy.crud.board.unit.controller;

import com.example.tddstudy.crud.controller.ReplyController;
import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.Reply;
import com.example.tddstudy.crud.domain.Member;
import com.example.tddstudy.crud.service.BoardService;
import com.example.tddstudy.crud.service.ReplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReplyController.class)
public class ReplyControllerTest {

    private static final String prefix = "/api/v1/reply";

    @MockBean
    private ReplyService replyService;

    @MockBean
    private BoardService boardService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("댓글 쓰기")
    public void saveReply() throws Exception {
        Member member = Member.builder()
                .id(1L)
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .id(1L)
                .title("ksb title")
                .content("ksb contents")
                .member(member)
                .build();
        Reply reply = Reply.builder()
                .id(1L)
                .content("ksb reply")
                .build();

        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("user", member.toJson());
        valueMap.add("board", board.toJson());
        valueMap.add("reply", reply.toJson());

        Board copiedBoard = board.copyOf();
        board.setReplies(List.of(reply));

        Reply copiedReply = reply.copyOf();
        copiedReply.setMember(member);
        copiedReply.setBoard(copiedBoard);

        given(replyService.save(any(Member.class), any(Board.class), any(Reply.class))).willReturn(copiedReply);
        given(boardService.findByTitle(board.getTitle())).willReturn(copiedBoard);

        RequestBuilder request = MockMvcRequestBuilders
                .post(prefix + "/save")
                .params(valueMap)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reply.getId()))
                .andExpect(jsonPath("$.board.id").value(board.getId()))
                .andExpect(jsonPath("$.user.id").value(member.getId()));

        assertEquals(boardService.findByTitle(board.getTitle()).getId(), copiedBoard.getId());
    }

    @Test
    @DisplayName("자신의 댓글들 보기")
    public void viewReplies() throws Exception {
        Member member = Member.builder()
                .id(1L)
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .id(1L)
                .title("ksb title")
                .content("ksb contents")
                .member(member)
                .build();
        Reply reply1 = Reply.builder()
                .id(1L)
                .member(member)
                .content("ksb reply1")
                .build();
        Reply reply2 = Reply.builder()
                .id(1L)
                .member(member)
                .content("ksb reply2")
                .build();

        List<Reply> replies = List.of(reply1, reply2);
        board.setReplies(replies);

        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("user", member.toJson());

        given(replyService.findByUserId(any(Member.class))).willReturn(replies);

        RequestBuilder request = MockMvcRequestBuilders
                .get(prefix + "/findbyuserid")
                .params(valueMap)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(replies.get(0).getId()))
                .andExpect(jsonPath("$.[1].id").value(replies.get(1).getId()));
    }

    @Test
    @DisplayName("자신의 댓글 수정")
    public void updateReply() throws Exception{
        Member member = Member.builder()
                .id(1L)
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .id(1L)
                .title("ksb title")
                .content("ksb contents")
                .member(member)
                .build();
        Reply reply = Reply.builder()
                .id(1L)
                .member(member)
                .content("ksb reply")
                .build();
        String updateContent = "kkk reply";

        List<Reply> replies = List.of(reply);
        board.setReplies(replies);

        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("user", member.toJson());
        valueMap.add("reply", reply.toJson());
        valueMap.add("update", updateContent);

        given(replyService.updateReplyContent(any(Member.class), any(Reply.class), any(String.class))).willReturn(true);

        RequestBuilder request = MockMvcRequestBuilders
                .put(prefix + "/update")
                .params(valueMap)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isUpdate").value(true));
    }

    @Test
    @DisplayName("댓글 삭제")
    public void deleteReply() throws Exception {
        Member member = Member.builder()
                .id(1L)
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .id(1L)
                .title("ksb title")
                .content("ksb contents")
                .member(member)
                .build();
        Reply reply = Reply.builder()
                .id(1L)
                .member(member)
                .content("ksb reply")
                .build();
        board.setReplies(new ArrayList<>());
        board.getReplies().add(reply);

        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("user", member.toJson());
        valueMap.add("board", board.toJson());
        valueMap.add("reply", reply.toJson());

        given(replyService.delete(any(Member.class), any(Board.class), any(Reply.class))).willReturn(true);

        RequestBuilder request = MockMvcRequestBuilders
                .delete(prefix + "/delete")
                .params(valueMap)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isDelete").value(true));
    }
}
