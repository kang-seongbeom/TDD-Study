package com.example.tddstudy.crud.board.unit.controller;

import com.example.tddstudy.crud.controller.BoardController;
import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.Member;
import com.example.tddstudy.crud.service.BoardService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
public class BoardControllerTest {

    private static final String prefix = "/api/v1/board";

    @MockBean
    private BoardService boardService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("게시글 쓰기")
    public void saveBoard() throws Exception {
        Member member = Member.builder()
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .title("ksb board")
                .content("ksb content")
                .build();

        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("member", member.toJson());
        valueMap.add("board", board.toJson());

        RequestBuilder request = MockMvcRequestBuilders
                .post(prefix + "/save")
                .params(valueMap)
                .contentType(MediaType.APPLICATION_JSON);

        board.setMember(member);
        given(boardService.save(any(Member.class), any(Board.class))).willReturn(board);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.member.id").value(member.getId()))
                .andExpect(jsonPath("$.title").value(board.getTitle()))
                .andExpect((jsonPath("$.content").value(board.getContent())));
    }

    @Test
    @DisplayName("게시글 이름으로 검색")
    public void findByTitle() throws Exception {
        Board board = Board.builder()
                .title("ksb board")
                .content("ksb content")
                .build();

        String findByTitle = board.getTitle();

        RequestBuilder request = MockMvcRequestBuilders
                .get(prefix + "/find")
                .param("title", findByTitle)
                .contentType(MediaType.APPLICATION_JSON);

        given(boardService.findByTitle(findByTitle)).willReturn(board);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(board.getTitle()))
                .andExpect((jsonPath("$.content").value(board.getContent())));
    }

    @Test
    @DisplayName("게시글 제목 수정")
    public void updateTitle() throws Exception {
        Member member = Member.builder()
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .member(member)
                .title("ksb board")
                .content("ksb content")
                .build();

        String update = "kkk board";

        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("member", member.toJson());
        valueMap.add("board", board.toJson());
        valueMap.add("update", update);

        RequestBuilder request = MockMvcRequestBuilders
                .put(prefix + "/updateTitle")
                .params(valueMap)
                .contentType(MediaType.APPLICATION_JSON);

        board.setTitle(update);
        given(boardService.updateTitle(any(Member.class), any(Board.class), any(String.class))).willReturn(board);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(update));
    }

    @Test
    @DisplayName("게시글 내용 수정")
    public void updateContent() throws Exception {
        Member member = Member.builder()
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .member(member)
                .title("ksb board")
                .content("ksb content")
                .build();

        String update = "kkk board";

        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("member", member.toJson());
        valueMap.add("board", board.toJson());
        valueMap.add("update", update);

        RequestBuilder request = MockMvcRequestBuilders
                .put(prefix + "/updateContent")
                .params(valueMap)
                .contentType(MediaType.APPLICATION_JSON);

        given(boardService.findByTitle(update)).willReturn(board);
        board.setContent(update);
        given(boardService.updateContent(any(Member.class), any(Board.class), any(String.class))).willReturn(board);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(update));
    }

    @Test
    @DisplayName("게시글 삭제")
    public void delete() throws Exception {
        Member member = Member.builder()
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .member(member)
                .title("ksb board")
                .content("ksb content")
                .build();

        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("member", member.toJson());
        valueMap.add("board", board.toJson());

        RequestBuilder request = MockMvcRequestBuilders
                .delete(prefix + "/delete")
                .params(valueMap)
                .contentType(MediaType.APPLICATION_JSON);

        given(boardService.delete(any(Member.class), any(Board.class))).willReturn(true);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isDelete").value(true));
    }
}
