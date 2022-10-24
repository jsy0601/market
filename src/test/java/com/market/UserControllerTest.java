package com.market;

import com.market.domain.User;
import com.market.dto.UserFormDto;
import com.market.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserService userService;
    private MockMvc mockMvc;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void createMember() throws Exception {
        UserFormDto memberFormDto = new UserFormDto();
        memberFormDto.setEmail("wjdwtjdus123111@naver.com");
        memberFormDto.setUsername("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("11111111");
        User member = User.createUser(memberFormDto, passwordEncoder);
        userService.saveUser(member);

        this.mockMvc.perform(post("/posts") // 1
                        .content("{\"title\": \"title\", \n\"content\": \"content\"}") // 2
                        .contentType(MediaType.APPLICATION_JSON)) // 3
                .andExpect(status().is4xxClientError()) // 4
                .andDo(document("post-create", // 5
                        requestFields( // 6
                                fieldWithPath("title").description("Post 제목"), // 7
                                fieldWithPath("content").description("Post 내용").optional() // 8
                        )
                ));
    }
}