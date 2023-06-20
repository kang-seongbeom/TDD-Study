package com.example.tddstudy.crud.board.unit;

import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.Reply;
import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.repository.BoardRepository;
import com.example.tddstudy.crud.repository.ReplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReplyRepositoryTest {

    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private BoardRepository boardRepository;

    @Test
    @DisplayName("댓글 쓰기")
    public void writeReply(){
        User user = User.builder()
                .id(1L)
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .id(1L)
                .title("ksb title")
                .content("ksb contents")
                .user(user)
                .build();
        Reply reply1 = Reply.builder()
                .id(1L)
                .board(board)
                .content("ksb reply1")
                .build();
        Reply reply2 = Reply.builder()
                .id(1L)
                .board(board)
                .content("ksb reply2")
                .build();

        List<Reply> replies = List.of(reply1, reply2);
        board.setReplies(replies);

        given(replyRepository.save(reply1)).willReturn(reply1);
        given(replyRepository.save(reply2)).willReturn(reply2);

        Board savedBoard1 = replyRepository.save(reply1).getBoard();
        Board savedBoard2 = replyRepository.save(reply2).getBoard();

        assertEquals("ksb", savedBoard1.getUser().getName());
        assertEquals("1234", savedBoard1.getUser().getPassword());
        assertEquals("ksb title", savedBoard1.getTitle());
        assertEquals("ksb contents", savedBoard1.getContent());
        assertEquals("ksb reply1", savedBoard1.getReplies().get(0).getContent());
        assertEquals("ksb reply2", savedBoard1.getReplies().get(1).getContent());

        assertEquals("ksb", savedBoard2.getUser().getName());
        assertEquals("1234", savedBoard2.getUser().getPassword());
        assertEquals("ksb title", savedBoard2.getTitle());
        assertEquals("ksb contents", savedBoard2.getContent());
        assertEquals("ksb reply1", savedBoard2.getReplies().get(0).getContent());
        assertEquals("ksb reply2", savedBoard2.getReplies().get(1).getContent());
    }

    @Test
    @DisplayName("자신의 댓글들 보기")
    public void viewReplies(){
        User user = User.builder()
                .id(1L)
                .name("ksb")
                .password("1234")
                .build();
        Board board1 = Board.builder()
                .id(1L)
                .title("ksb title1")
                .content("ksb contents1")
                .user(user)
                .build();
        Board board2 = Board.builder()
                .id(2L)
                .title("ksb title2")
                .content("ksb contents2")
                .user(user)
                .build();
        Reply reply1 = Reply.builder()
                .id(1L)
                .board(board1)
                .content("ksb reply1")
                .build();
        Reply reply2 = Reply.builder()
                .id(1L)
                .board(board1)
                .content("ksb reply2")
                .build();
        Reply reply3 = Reply.builder()
                .id(1L)
                .board(board2)
                .content("ksb reply3")
                .build();

        board1.setReplies(List.of(reply1, reply2));
        board2.setReplies(List.of(reply3));

        List<Board> userBoards = List.of(board1, board2);
        given(boardRepository.findByUserId(user.getId())).willReturn(userBoards);

        List<Board> findUserBoards = boardRepository.findByUserId(user.getId());

        assertEquals(findUserBoards.get(0).getReplies().get(0), reply1);
        assertEquals(findUserBoards.get(0).getReplies().get(1), reply2);
        assertEquals(findUserBoards.get(1).getReplies().get(0), reply3);
    }

    @Test
    @DisplayName("자신의 댓글 수정")
    public void updateReply(){
        User user = User.builder()
                .id(1L)
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .id(1L)
                .title("ksb title")
                .content("ksb contents")
                .user(user)
                .build();
        Reply reply1 = Reply.builder()
                .id(1L)
                .board(board)
                .content("ksb reply")
                .build();

        board.setReplies(List.of(reply1));
        List<Board> userBoards = List.of(board);

        given(boardRepository.findByUserId(user.getId())).willReturn(userBoards);

        boardRepository.findByUserId(user.getId()).get(0).getReplies().get(0).setContent("kkk reply");
        replyRepository.save(userBoards.get(0).getReplies().get(0));

        given(boardRepository.findByUserId(user.getId())).willReturn(userBoards);

        assertEquals("kkk reply", boardRepository.findByUserId(user.getId()).get(0).getReplies().get(0).getContent());
    }

    @Test
    @DisplayName("댓글 삭제")
    public void deleteReply(){
        User user = User.builder()
                .id(1L)
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .id(1L)
                .title("ksb title")
                .content("ksb contents")
                .user(user)
                .build();
        Reply reply1 = Reply.builder()
                .id(1L)
                .board(board)
                .content("ksb reply")
                .build();
        board.setReplies(List.of(reply1));

        given(boardRepository.save(board)).willReturn(board);
        Board saveBoard = boardRepository.save(board);

        replyRepository.delete(saveBoard.getReplies().get(0));
        given(boardRepository.save(saveBoard)).willReturn(saveBoard);
        boardRepository.save(saveBoard);

        given(replyRepository.findById(reply1.getId())).willReturn(null);
        assertNull(replyRepository.findById(reply1.getId()));
    }
}
