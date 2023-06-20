package com.example.tddstudy.crud.user.unit;

import com.example.tddstudy.crud.controller.UserController;
import com.example.tddstudy.crud.domain.User;
import com.example.tddstudy.crud.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String prefix = "/api/v1/user";

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("회원 가입")
    public void signUp() throws Exception {
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        User user = User.builder().id(id).name(name).password(password).build();

        given(userService.save(any(User.class))).willReturn(user);

        RequestBuilder request = MockMvcRequestBuilders
                .post(prefix + "/signup")
                .content(user.toJson())
                .contentType(MediaType.APPLICATION_JSON);

        requestController(request, user);
    }

    @Test
    @DisplayName("회원 정보 이름으로 검색")
    public void findByNameUserInDB() throws Exception {
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        User user = User.builder().id(id).name(name).password(password).build();

        given(userService.findByName(name)).willReturn(user);

        RequestBuilder request = MockMvcRequestBuilders
                .get(prefix +"/findbyname" + "?name=" + name)
                .contentType(MediaType.APPLICATION_JSON);

        requestController(request, user);
    }

    @Test
    @DisplayName("로그인")
    public void login() throws Exception {
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        User user = User.builder().id(id).name(name).password(password).build();

        given(userService.login(any(User.class))).willReturn(true);

        RequestBuilder request = MockMvcRequestBuilders
                .post(prefix +"/login")
                .content(user.toJson())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isLogin").value(true));
    }

    @Test
    @DisplayName("회원 이름 수정")
    public void updateUser() throws Exception {
        Long id = 1L;
        String name = "ksb";
        String password = "1234";
        String updateName = "kkk";

        User user = User.builder().id(id).name(name).password(password).build();
        User updateUser = user.copyOf();
        updateUser.setName(updateName);

        given(userService.updateUser(any(User.class), any(String.class))).willReturn(updateUser);

        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("name", updateUser.getName());
        valueMap.add("password", updateUser.getPassword());
        valueMap.add("updateName", updateUser.getName());

        RequestBuilder request = MockMvcRequestBuilders
                .put(prefix +"/updatename")
                .params(valueMap)
                .contentType(MediaType.APPLICATION_JSON);

        requestController(request, updateUser);
    }

    @Test
    @DisplayName("회원 삭제")
    public void deleteUser() throws Exception {
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        User user = User.builder().id(id).name(name).password(password).build();

        given(userService.delete(any(User.class))).willReturn(true);

        RequestBuilder request = MockMvcRequestBuilders
                .delete(prefix +"/delete")
                .content(user.toJson())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isDelete").value(true));

    }

    private void requestController(RequestBuilder request, User user) throws Exception {
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.password").value(user.getPassword()));
    }
}
