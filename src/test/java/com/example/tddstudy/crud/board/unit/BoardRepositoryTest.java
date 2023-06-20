package com.example.tddstudy.crud.board.unit;

import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BoardRepositoryTest {

    @Mock
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 쓰기")
    public void writeBoard(){
        Long id = 1L;
        String title = "ksb board";
        String content = "ksb content";

        Board board = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();

        given(boardRepository.save(board)).willReturn(board);

        Board write = boardRepository.save(board);

        assertEquals(board.getId(), write.getId());
        assertEquals(board.getTitle(), write.getTitle());
        assertEquals(board.getContent(), write.getContent());
    }

    @Test
    @DisplayName("게시글 이름으로 검색")
    public void findByTitle(){
        Long id = 1L;
        String title = "ksb board";
        String content = "ksb content";

        Board board = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();

        given(boardRepository.save(board)).willReturn(board);
        boardRepository.save(board);

        given(boardRepository.findByTitle(title)).willReturn(Optional.of(board));
        Board find = boardRepository.findByTitle(title).get();

        assertEquals(board.getId(), find.getId());
        assertEquals(board.getTitle(), find.getTitle());
        assertEquals(board.getContent(), find.getContent());
    }

    @Test
    @DisplayName("게시글 수정")
    public void updateBoard(){
        Long id = 1L;
        String title = "ksb board";
        String content = "ksb content";
        String updateTitle = "kkk board";
        String updateContent = "kkk content";

        Board board = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();

        Board update = board.copyOf();

        given(boardRepository.save(board)).willReturn(board);
        given(boardRepository.save(update)).willReturn(update);
        given(boardRepository.findById(update.getId())).willReturn(Optional.of(update));

        boardRepository.save(board);
        boardRepository.save(update);

        update = boardRepository.findById(update.getId()).get();

        update.setTitle(updateTitle); // dirty checking
        update.setContent(updateContent);

        update = boardRepository.save(update);

        assertEquals(board.getId(), update.getId());
        assertEquals(updateTitle, update.getTitle());
        assertEquals(updateContent, update.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    public void delete(){
        Long id = 1L;
        String title = "ksb board";
        String content = "ksb content";

        Board board = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();

        given(boardRepository.save(board)).willReturn(board);
        boardRepository.save(board);

        boardRepository.deleteById(board.getId());
        given(boardRepository.findById(id)).willReturn(null);
        given(boardRepository.findByTitle(title)).willReturn(null);

        assertNull(boardRepository.findById(id));
        assertNull(boardRepository.findByTitle(title));
    }
}
