package com.example.tddstudy.crud.board.unit.repository;

import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.repository.BoardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application.yml")
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void saveBoard() {
        String title = "ksb board";
        String content = "ksb content";

        Board board = Board.builder()
                .title(title)
                .content(content)
                .build();

        Board write = boardRepository.save(board);

        assertNotNull(write.getId());
        assertEquals(board.getTitle(), write.getTitle());
        assertEquals(board.getContent(), write.getContent());
    }

    @Test
    public void findByTitle() throws JsonProcessingException {
        String title = "ksb board";
        String content = "ksb content";

        Board board = Board.builder()
                .title(title)
                .content(content)
                .build();

        boardRepository.save(board);

        Board find = boardRepository.findByTitle(title).get();

        assertNotNull(board.getId());
        assertEquals(board.getTitle(), find.getTitle());
        assertEquals(board.getContent(), find.getContent());
    }

    @Test
    public void updateBoard(){
        String title = "ksb board";
        String content = "ksb content";
        String updateTitle = "kkk board";
        String updateContent = "kkk content";

        Board board = Board.builder()
                .title(title)
                .content(content)
                .build();

        Board update = board.copyOf();

        boardRepository.save(board);
        Board savedUpdated = boardRepository.save(update);

        update = boardRepository.findById(savedUpdated.getId()).get();

        update.setTitle(updateTitle); // dirty checking
        update.setContent(updateContent);

        update = boardRepository.save(update);

        assertEquals(savedUpdated.getId(), update.getId());
        assertEquals(updateTitle, update.getTitle());
        assertEquals(updateContent, update.getContent());
    }

    @Test
    public void delete(){
        String title = "ksb board";
        String content = "ksb content";

        Board board = Board.builder()
                .title(title)
                .content(content)
                .build();

        Board savedBoard = boardRepository.save(board);

        boardRepository.deleteById(board.getId());

        assertEquals(boardRepository.findById(savedBoard.getId()), Optional.empty());
        assertEquals(boardRepository.findByTitle(savedBoard.getTitle()), Optional.empty());
    }
}
