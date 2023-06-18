package com.example.tddstudy.learning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class LearningDto {
    long id;
    String name;

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(LearningDto.builder().id(id).name(name).build());
    }
}
