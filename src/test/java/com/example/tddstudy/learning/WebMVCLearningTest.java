package com.example.tddstudy.learning;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class WebMVCLearningTest {

    private static final String prefix = "/learning/api/v1";

    @Autowired
    private MockMvc mockMvc;

    private LearningDto requestDto = LearningDto.builder()
            .id(1L)
            .name("ksb")
            .build();

    @Test
    public void getPathVariableTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get(prefix + "/get/pathvariable/" + requestDto.id + "/" + requestDto.name)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestDto.id))
                .andExpect(jsonPath("$.name").value(requestDto.name));
    }

    @Test
    public void getRequestParamTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get(prefix + "/get/requestparam?id=" + requestDto.id + "&name=" + requestDto.name)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestDto.id))
                .andExpect(jsonPath("$.name").value(requestDto.name));
    }

    @Test
    public void getRequestBodyTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get(prefix + "/get/requestbody")
                .content(requestDto.toJson())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestDto.id))
                .andExpect(jsonPath("$.name").value(requestDto.name));
    }
}
