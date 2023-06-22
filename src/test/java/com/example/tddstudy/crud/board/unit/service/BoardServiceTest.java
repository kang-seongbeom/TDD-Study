package com.example.tddstudy.crud.board.unit.service;

import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.repository.BoardRepository;
import com.example.tddstudy.crud.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
    @DisplayName("게시글 쓰기")
    public void writeBoard(){
        Long id = 1L;
        String title = "ksb board";
        String content = "ksb content";

        User user = User.builder()
                .id(1L)
                .name("ksb")
                .password("1234")
                .build();

        Board board = Board.builder()
                .title(title)
                .content(content)
                .build();

        board.setId(id); // id는 db 저장시 자동으로 생성
        given(boardRepository.save(board)).willReturn(board);

        Board saveBoard = boardService.save(user, board);
        assertEquals(saveBoard.getUser(), user);
        assertEquals(saveBoard.getId(), board.getId());
        assertEquals(saveBoard.getTitle(), board.getTitle());
        assertEquals(saveBoard.getContent(), board.getContent());
    }

    @Test
    @DisplayName("게시글 이름으로 검색")
    public void findByTitle(){
        Long id = 1L;
        String title = "ksb board";
        String content = "ksb content";

        Board board = Board.builder()
                .title(title)
                .content(content)
                .build();

        board.setId(id);
        given(boardRepository.findByTitle(title)).willReturn(Optional.of(board));

        Board findBoard = boardService.findByTitle(title);
        assertEquals(findBoard.getId(), board.getId());
        assertEquals(findBoard.getTitle(), board.getTitle());
        assertEquals(findBoard.getContent(), board.getContent());
    }

    @Test
    @DisplayName("게시글 제목 수정")
    public void updateTitleBoard(){
        Long id = 1L;
        String title = "ksb board";
        String content = "ksb content";
        String updateTitle = "kkk board";

        User user = User.builder()
                .id(1L)
                .name("ksb")
                .password("1234")
                .build();

        Board board = Board.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();

        board.setId(id);
        given(boardRepository.findById(id)).willReturn(Optional.of(board));

        Board updateBoard = boardService.updateTitle(user, board, updateTitle);

        board.setTitle(updateTitle);

        assertEquals(updateBoard.getId(), board.getId());
        assertEquals(updateBoard.getTitle(), board.getTitle());
        assertEquals(updateBoard.getContent(), board.getContent());
    }

    @Test
    @DisplayName("게시글 내용 수정")
    public void updateContentBoard(){
        Long id = 1L;
        String title = "ksb board";
        String content = "ksb content";
        String updateContent = "kkk board";

        User user = User.builder()
                .id(1L)
                .name("ksb")
                .password("1234")
                .build();

        Board board = Board.builder()
                .title(title)
                .user(user)
                .content(content)
                .build();

        board.setId(id);
        given(boardRepository.findById(id)).willReturn(Optional.of(board));

        Board updateBoard = boardService.updateContent(user, board, updateContent);

        board.setContent(updateContent);

        assertEquals(updateBoard.getId(), board.getId());
        assertEquals(updateBoard.getTitle(), board.getTitle());
        assertEquals(updateBoard.getContent(), board.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    public void delete(){
        Long id = 1L;
        String title = "ksb board";
        String content = "ksb content";

        User user = User.builder()
                .id(1L)
                .name("ksb")
                .password("1234")
                .build();

        Board board = Board.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();

        board.setId(id);

        given(boardRepository.findById(board.getId())).willReturn(Optional.of(board));
        boolean isDelete = boardService.delete(user, board);
        assertTrue(isDelete);
    }

}
