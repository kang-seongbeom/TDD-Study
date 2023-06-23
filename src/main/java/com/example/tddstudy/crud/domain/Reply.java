package com.example.tddstudy.crud.domain;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replyId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @Column
    private String content;

    public Reply copyOf(){
        return Reply.builder()
                .id(id)
                .user(user)
                .board(board)
                .content(content)
                .build();
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(Reply.builder().id(id).user(user).board(board).content(content).build());
    }
}
