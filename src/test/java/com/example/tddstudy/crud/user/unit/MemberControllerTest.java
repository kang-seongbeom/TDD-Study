package com.example.tddstudy.crud.user.unit;

import com.example.tddstudy.crud.controller.MemberController;
import com.example.tddstudy.crud.domain.Member;
import com.example.tddstudy.crud.service.MemberService;
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

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    private static final String prefix = "/api/v1/user";

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("회원 가입")
    public void signUp() throws Exception {
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        Member member = Member.builder().id(id).name(name).password(password).build();

        given(memberService.save(any(Member.class))).willReturn(member);

        RequestBuilder request = MockMvcRequestBuilders
                .post(prefix + "/signup")
                .content(member.toJson())
                .contentType(MediaType.APPLICATION_JSON);

        requestController(request, member);
    }

    @Test
    @DisplayName("회원 정보 이름으로 검색")
    public void findByNameUserInDB() throws Exception {
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        Member member = Member.builder().id(id).name(name).password(password).build();

        given(memberService.findByName(name)).willReturn(member);

        RequestBuilder request = MockMvcRequestBuilders
                .get(prefix +"/findbyname" + "?name=" + name)
                .contentType(MediaType.APPLICATION_JSON);

        requestController(request, member);
    }

    @Test
    @DisplayName("로그인")
    public void login() throws Exception {
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        Member member = Member.builder().id(id).name(name).password(password).build();

        given(memberService.login(any(Member.class))).willReturn(true);

        RequestBuilder request = MockMvcRequestBuilders
                .post(prefix +"/login")
                .content(member.toJson())
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

        Member member = Member.builder().id(id).name(name).password(password).build();
        Member updateMember = member.copyOf();
        updateMember.setName(updateName);

        given(memberService.updateUser(any(Member.class), any(String.class))).willReturn(updateMember);

        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("name", updateMember.getName());
        valueMap.add("password", updateMember.getPassword());
        valueMap.add("updateName", updateMember.getName());

        RequestBuilder request = MockMvcRequestBuilders
                .put(prefix +"/updatename")
                .params(valueMap)
                .contentType(MediaType.APPLICATION_JSON);

        requestController(request, updateMember);
    }

    @Test
    @DisplayName("회원 삭제")
    public void deleteUser() throws Exception {
        Long id = 1L;
        String name = "ksb";
        String password = "1234";

        Member member = Member.builder().id(id).name(name).password(password).build();

        given(memberService.delete(any(Member.class))).willReturn(true);

        RequestBuilder request = MockMvcRequestBuilders
                .delete(prefix +"/delete")
                .content(member.toJson())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isDelete").value(true));

    }

    private void requestController(RequestBuilder request, Member member) throws Exception {
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(member.getId()))
                .andExpect(jsonPath("$.name").value(member.getName()))
                .andExpect(jsonPath("$.password").value(member.getPassword()));
    }
}
