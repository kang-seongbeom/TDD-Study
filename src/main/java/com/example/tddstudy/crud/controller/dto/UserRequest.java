package com.example.tddstudy.crud.controller.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @JsonAlias("name")
    private String name;

    @JsonAlias("password")
    private String password;

    @JsonAlias("updateName")
    private String updateName;
}
