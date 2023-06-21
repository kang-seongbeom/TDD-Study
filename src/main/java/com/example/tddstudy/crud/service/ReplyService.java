package com.example.tddstudy.crud.service;

import com.example.tddstudy.crud.domain.Board;
import com.example.tddstudy.crud.domain.Reply;
import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.repository.BoardRepository;
import com.example.tddstudy.crud.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReplyService {


    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    public Reply save(User replyUser, Long boardId, Reply reply) {
        Board board = boardRepository.findById(boardId).orElseThrow();

        if(board.getReplies() == null){
            board.setReplies(new ArrayList<>());
        }

        board.getReplies().add(reply);
        reply.setBoard(board);
        reply.setUser(replyUser);
        return reply;
    }

    public List<Reply> findByUserId(Long replyUserId) {
        return replyRepository.findByUserId(replyUserId);
    }

    public boolean updateReplyContent(User replyUser, Long replyId, String updateContent) {
        Reply reply = replyRepository.findById(replyId).orElseThrow();
        if(reply.getUser().getId().equals(replyUser.getId())) {
            reply.setContent(updateContent);
            return true;
        }
        return false;
    }

    public boolean delete(User replyUser, Long boardId, Reply reply) {
        Board board = boardRepository.findById(boardId).orElseThrow();

        if(board.getReplies() != null){
            int i;
            for (i = 0; i < board.getReplies().size(); i++) {
                if(replyUser.getId().equals(reply.getUser().getId()) && // 댓글 작성자 본인이면서
                        board.getReplies().get(i).getId().equals(reply.getId())){ // 삭제할 댓글을 찾았다면
                    board.getReplies().remove(i);
                    replyRepository.delete(reply);
                    return true;
                }
            }
        }
        return false;
    }
}
