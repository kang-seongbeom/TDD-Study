package com.example.tddstudy.crud.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.persistence.*;

@Entity(name = "Member")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long id;

    @Column
    private String name;

    @Column
    private String password;

    public Member copyOf(){
        return Member.builder()
                .name(this.name)
                .password(this.password)
                .build();
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(Member.builder().id(id).name(name).password(password).build());
    }
}
