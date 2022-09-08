package com.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.model.user.UserLogIn;
import com.user.dto.UserLogInDTO;
import com.user.model.user.UserRegister;
import com.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService mockUserService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRegister userRegister;
    private UserLogIn userLogIn;
    private UserLogInDTO userLogInDTO;

    @BeforeEach
    void init(){
        userRegister = UserRegister.builder()
                .userName("Ramesh Shrestha")
                .userPassword("Test1234")
                .isEmailVerify(false)
                .emailAddress("shresthashare@gmail.com")
                .token("")
                .accessCode(0)
                .build();
        userLogIn = UserLogIn.builder()
                .emailAddress("shresthashare@gmail.com")
                .userPassword("Test1234")
                .build();
        userLogInDTO = UserLogInDTO.builder().build();
    }

    @Test
    void whenValidInputUserRegister_thenReturn200() throws Exception {
        when(mockUserService.userRegister(userRegister)).thenReturn(userRegister);
        mockMvc.perform(post("/userRegister")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegister)))
                .andExpect(status().isOk());
    }

    @Test
    void whenUserRegisterEmailIsInvalidInput_thenReturn400() throws Exception{
        userRegister.setEmailAddress("asfdsdf");
        when(mockUserService.userRegister(userRegister)).thenReturn(userRegister);
        mockMvc.perform(post("/userRegister")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegister)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserRegisterPasswordIsInvalidInput_thenReturn400() throws Exception{
        userRegister.setUserPassword("Test");
        when(mockUserService.userRegister(userRegister)).thenReturn(userRegister);
        mockMvc.perform(post("/userRegister")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegister)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserRegisterUserNameIsInvalidInput_thenReturn400() throws Exception{
        userRegister.setUserName("");
        when(mockUserService.userRegister(userRegister)).thenReturn(userRegister);
        mockMvc.perform(post("/userRegister")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegister)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserRegisterEmailPasswordUserNameAreNull_thenReturn400() throws Exception{
        userRegister.setUserName(null);
        userRegister.setUserPassword(null);
        userRegister.setEmailAddress(null);
        when(mockUserService.userRegister(userRegister)).thenReturn(userRegister);
        mockMvc.perform(post("/userRegister")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegister)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenValidInputUserLogIn_thenReturn200() throws Exception{
        when(mockUserService.userLogIn(userLogIn)).thenReturn(userLogInDTO);
        mockMvc.perform(post("/userLogIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLogIn)))
                .andExpect(status().isOk());
    }

    @Test
    void whenLogInEmailIsInvalidInput_thenReturn400() throws Exception{
        userLogIn.setEmailAddress("shresthashare");
        mockMvc.perform(post("/userLogIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLogIn)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenLogInPasswordIsInvalidInput_thenReturn400() throws Exception{
        userLogIn.setUserPassword("Test");
        mockMvc.perform(post("/userLogIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLogIn)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenLogInEmailAndPasswordIsNull_thenReturn400() throws Exception{
        userLogIn.setUserPassword(null);
        userLogIn.setEmailAddress(null);
        mockMvc.perform(post("/userLogIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLogIn)))
                .andExpect(status().isBadRequest());
    }
}
