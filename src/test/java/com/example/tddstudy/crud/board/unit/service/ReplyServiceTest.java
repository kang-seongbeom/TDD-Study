package com.example.tddstudy.crud.board.unit.service;

import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.Reply;
import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.repository.BoardRepository;
import com.example.tddstudy.crud.repository.ReplyRepository;
import com.example.tddstudy.crud.service.ReplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReplyServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private ReplyService replyservice;

    @Test
    @DisplayName("댓글 쓰기")
    public void writeReply(){
        User replyUser = User.builder()
                .id(123L)
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .id(11L)
                .title("ksb title")
                .content("ksb contents")
                .build();
        Reply reply = Reply.builder()
                .id(1L)
                .content("ksb reply")
                .build();

        given(boardRepository.findById(board.getId())).willReturn(Optional.of(board));
        Reply result = replyservice.save(replyUser, board, reply);

        assertEquals(result.getBoard().getId(), board.getId());
        assertEquals(result.getUser().getId(), replyUser.getId());
        assertEquals(result.getBoard().getReplies().get(0).getId(), reply.getId());
    }

    @Test
    @DisplayName("자신의 댓글들 보기")
    public void viewReplies(){
        User replyUser = User.builder()
                .id(123L)
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .id(11L)
                .title("ksb title")
                .content("ksb contents")
                .build();
        Reply reply1 = Reply.builder()
                .id(1L)
                .user(replyUser)
                .board(board)
                .content("ksb reply1")
                .build();
        Reply reply2 = Reply.builder()
                .id(2L)
                .user(replyUser)
                .board(board)
                .content("ksb reply2")
                .build();

        List<Reply> replies = List.of(reply1, reply2);
        board.setReplies(replies);

        given(replyRepository.findByUserId(replyUser.getId())).willReturn(replies);
        List<Reply> userReplies = replyservice.findByUserId(replyUser);

        for (int i = 0; i < userReplies.size(); i++) {
            assertEquals(userReplies.get(i).getId(), replies.get(i).getId());
            assertEquals(userReplies.get(i).getUser(), replies.get(i).getUser());
            assertEquals(userReplies.get(i).getContent(), replies.get(i).getContent());
        }
    }

    @Test
    @DisplayName("자신의 댓글 수정")
    public void updateReply(){
        User replyUser = User.builder()
                .id(123L)
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .id(11L)
                .title("ksb title")
                .content("ksb contents")
                .build();
        Reply reply = Reply.builder()
                .id(1L)
                .user(replyUser)
                .board(board)
                .content("ksb reply")
                .build();
        String updateContent = "kkk reply";

        List<Reply> replies = List.of(reply);
        board.setReplies(replies);

        given(replyRepository.findById(reply.getId())).willReturn(Optional.of(reply));
        boolean isUpdated = replyservice.updateReplyContent(replyUser, reply, updateContent);

        assertTrue(isUpdated);
        assertEquals(reply.getContent(), updateContent);
    }

    @Test
    @DisplayName("댓글 삭제")
    public void deleteReply(){
        User replyUser = User.builder()
                .id(123L)
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .id(11L)
                .title("ksb title")
                .content("ksb contents")
                .build();
        Reply reply = Reply.builder()
                .id(1L)
                .user(replyUser)
                .board(board)
                .content("ksb reply")
                .build();

        List<Reply> replies = new ArrayList<>();
        replies.add(reply);
        board.setReplies(replies);

        given(boardRepository.findById(board.getId())).willReturn(Optional.of(board));
        replyservice.delete(replyUser, board, reply);

        assertEquals(0, board.getReplies().size());
    }

    @Test
    @DisplayName("게시글 삭제시 댓글 같이 삭제")
    public void deleteBoardWithReplies(){
        User replyUser = User.builder()
                .id(123L)
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .id(11L)
                .title("ksb title")
                .content("ksb contents")
                .build();
        Reply reply1 = Reply.builder()
                .id(1L)
                .user(replyUser)
                .board(board)
                .content("ksb reply1")
                .build();
        Reply reply2 = Reply.builder()
                .id(2L)
                .user(replyUser)
                .board(board)
                .content("ksb reply2")
                .build();

        List<Reply> replies = List.of(reply1, reply2);
        board.setReplies(replies);

        boardRepository.delete(board);
        given(replyRepository.findById(reply1.getId())).willReturn(null);
        given(replyRepository.findById(reply2.getId())).willReturn(null);

        assertNull(replyRepository.findById(reply1.getId()));
        assertNull(replyRepository.findById(reply2.getId()));
    }

}
