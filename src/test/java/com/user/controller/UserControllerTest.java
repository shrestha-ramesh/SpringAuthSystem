package com.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.model.user.UserLogIn;
import com.user.dto.UserLogInDTO;
import com.user.model.user.UserRegister;
import com.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @Autowired
    private UserController userControllerTest;

    private UserRegister userRegister;
    private UserLogIn userLogIn;


    private UserLogInDTO userLogInDTO = UserLogInDTO.builder().build();

    @BeforeEach
    void init(){
        userRegister = getUserRegister();
        userLogIn = getUserLogIn();
    }

    @Test
    void whenValidInputUserRegister_thenReturn200() throws Exception {
        when(mockUserService.userRegister(userRegister)).thenReturn(userRegister);
        mockMvc.perform(post("/userRegister")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegister)))
                .andExpect(status().isCreated());
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
        userLogIn.setEmailAddress("shrestha6941");
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
    private UserRegister getUserRegister(){
        return UserRegister.builder()
                .userName("Ramesh")
                .userPassword("Test12345")
                .isEmailVerify(false)
                .emailAddress("shrestha6941@gmail.com")
                .token("")
                .accessCode(0)
                .build();
    }
    private UserLogIn getUserLogIn(){
        return UserLogIn.builder()
                .emailAddress("shrestha6941@gmail.com")
                .userPassword("Test1234")
                .build();
    }
}
