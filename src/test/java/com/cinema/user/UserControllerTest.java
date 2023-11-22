package com.cinema.user;

import com.cinema.user.dto.CreatedUserDto;
import com.cinema.user.dto.UserRequestDto;
import com.cinema.user.dto.UserResponseDto;
import com.cinema.user.userEnum.AccountType;
import com.cinema.user.userEnum.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest {
    @MockBean
    private UserService service;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private static UserRequestDto userRequestDto;
    private static String useRequestDtoJson;
    private static CreatedUserDto createdUserDto;
    private static User user;
    private static UserResponseDto userResponseDto;
    private static UserResponseDto secondUserResponseDto;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        userRequestDto = new UserRequestDto("A", "B", "ab@gmail.cpm", "password", "password", UserRole.ADMIN);
        userResponseDto = new UserResponseDto("A", "B", "ab@gmail.cpm", UserRole.ADMIN);

        useRequestDtoJson = objectMapper.writeValueAsString(userRequestDto);

    }


    @Test
    @DisplayName("Should save user account")
    void should_save_user() throws Exception {
        given(service.registration(userRequestDto)).willReturn(createdUserDto);

        mockMvc.perform(post("/users/registration")
                        .content(useRequestDtoJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should find user by id")
    void should_find_user_by_id() throws Exception {
        given(service.findUserById(1L)).willReturn(userResponseDto);

        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("A"))
                .andExpect(jsonPath("$.lastName").value("B"))
                .andExpect(jsonPath("$.email").value("ab@gmail.cpm"));
    }
}
