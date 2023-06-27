package com.example.tddstudy.crud.board.unit.repository;

import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.Reply;
import com.example.tddstudy.crud.domain.Member;
import com.example.tddstudy.crud.repository.BoardRepository;
import com.example.tddstudy.crud.repository.MemberRepository;
import com.example.tddstudy.crud.repository.ReplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@TestPropertySource(locations = "classpath:application.yml")
public class ReplyRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("댓글 쓰기")
    public void writeReply(){
        Member member = Member.builder()
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .title("ksb title")
                .content("ksb contents")
                .member(member)
                .replies(new ArrayList<>())
                .build();
        Reply reply1 = Reply.builder()
                .board(board)
                .content("ksb reply1")
                .build();
        Reply reply2 = Reply.builder()
                .board(board)
                .content("ksb reply2")
                .build();

        memberRepository.save(member);
        boardRepository.save(board);
        replyRepository.save(reply1);
        replyRepository.save(reply2);

        board.getReplies().add(reply1);
        board.getReplies().add(reply2);

        boardRepository.save(board);

        Board savedBoard1 = reply1.getBoard();
        Board savedBoard2 = reply2.getBoard();

        assertEquals("ksb", savedBoard1.getMember().getName());
        assertEquals("1234", savedBoard1.getMember().getPassword());
        assertEquals("ksb title", savedBoard1.getTitle());
        assertEquals("ksb contents", savedBoard1.getContent());
        assertEquals("ksb reply1", savedBoard1.getReplies().get(0).getContent());
        assertEquals("ksb reply2", savedBoard1.getReplies().get(1).getContent());

        assertEquals("ksb", savedBoard2.getMember().getName());
        assertEquals("1234", savedBoard2.getMember().getPassword());
        assertEquals("ksb title", savedBoard2.getTitle());
        assertEquals("ksb contents", savedBoard2.getContent());
        assertEquals("ksb reply1", savedBoard2.getReplies().get(0).getContent());
        assertEquals("ksb reply2", savedBoard2.getReplies().get(1).getContent());
    }

    @Test
    @DisplayName("자신의 댓글들 보기")
    public void viewReplies(){
        Member member = Member.builder()
                .name("ksb")
                .password("1234")
                .build();
        Board board1 = Board.builder()
                .title("ksb title1")
                .content("ksb contents1")
                .member(member)
                .build();
        Board board2 = Board.builder()
                .title("ksb title2")
                .content("ksb contents2")
                .member(member)
                .build();
        Reply reply1 = Reply.builder()
                .board(board1)
                .content("ksb reply1")
                .build();
        Reply reply2 = Reply.builder()
                .board(board1)
                .content("ksb reply2")
                .build();
        Reply reply3 = Reply.builder()
                .board(board2)
                .content("ksb reply3")
                .build();

        memberRepository.save(member);
        boardRepository.save(board1);
        boardRepository.save(board2);
        replyRepository.save(reply1);
        replyRepository.save(reply2);
        replyRepository.save(reply3);

        List<Reply> board1Replies = new ArrayList<>();
        List<Reply> board2Replies = new ArrayList<>();
        board1Replies.add(reply1);
        board1Replies.add(reply2);
        board2Replies.add(reply3);
        board1.setReplies(board1Replies);
        board2.setReplies(board2Replies);
        boardRepository.save(board1);
        boardRepository.save(board2);

        List<Board> findUserBoards = boardRepository.findByMemberId(member.getId());

        assertEquals(findUserBoards.get(0).getReplies().get(0), reply1);
        assertEquals(findUserBoards.get(0).getReplies().get(1), reply2);
        assertEquals(findUserBoards.get(1).getReplies().get(0), reply3);
    }

    @Test
    @DisplayName("자신의 댓글 수정")
    public void updateReply(){
        Member member = Member.builder()
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .title("ksb title")
                .content("ksb contents")
                .replies(new ArrayList<>())
                .member(member)
                .build();
        Reply reply = Reply.builder()
                .board(board)
                .content("ksb reply")
                .build();

        memberRepository.save(member);
        boardRepository.save(board);
        replyRepository.save(reply);

        board.getReplies().add(reply);
        boardRepository.save(board);

        reply.setContent("kkk reply");
        replyRepository.save(reply);

        assertEquals(board.getReplies().get(0).getContent(), "kkk reply");
    }

    @Test
    @DisplayName("댓글 삭제")
    public void deleteReply(){
        Member member = Member.builder()
                .name("ksb")
                .password("1234")
                .build();
        Board board = Board.builder()
                .title("ksb title")
                .content("ksb contents")
                .member(member)
                .replies(new ArrayList<>())
                .build();
        Reply reply = Reply.builder()
                .board(board)
                .content("ksb reply")
                .build();

        memberRepository.save(member);
        boardRepository.save(board);
        replyRepository.save(reply);

        board.getReplies().add(reply);
        boardRepository.save(board);

        replyRepository.delete(board.getReplies().get(0));

        assertEquals(Optional.empty(), replyRepository.findById(reply.getId()));
    }
}
