package com.example.tddstudy.crud.board.unit.repository;

import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:test-application.yml")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class BoardRepositoryTest {

    @Autowired
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

        boardRepository.save(board);

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

        boardRepository.save(board);
        boardRepository.save(update);

        update = boardRepository.findById(update.getId()).get();

        update.setTitle(updateTitle); // dirty checking
        update.setContent(updateContent);

        update = boardRepository.save(update);

        assertEquals(board.getId(), update.getId());
        assertEquals(updateTitle, update.getTitle());
        assertEquals(updateContent, update.getContent());
        assertNotEquals(board.getContent(), update.getContent());
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

        boardRepository.save(board);

        boardRepository.deleteById(board.getId());

        assertEquals(boardRepository.findById(id), Optional.empty());
        assertEquals(boardRepository.findByTitle(title), Optional.empty());
    }
}
