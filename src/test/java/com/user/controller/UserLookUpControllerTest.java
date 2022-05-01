package com.user.controller;

import com.user.model.UserRegister;
import com.user.model.exception.EmailNotFoundException;
import com.user.service.UserLookUpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;




import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserLookUpController.class)
public class UserLookUpControllerTest {

    @MockBean
    private UserLookUpService mockUserLookUpService;

    @Autowired
    private MockMvc mockMvc;

    UserRegister userRegister;
    String emailAddress;
    int accessCode;

    @BeforeEach
    void init(){
        userRegister = UserRegister.builder().build();
        emailAddress="shresthashare@gmail.com";
        accessCode = 12345;
    }

    @Test
    void whenUserLookUpValid_thenReturn200() throws Exception{
        when(mockUserLookUpService.userLookUp(emailAddress)).thenReturn(userRegister);
        mockMvc.perform(get("/userLookUp/{userLookUp}",emailAddress)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenUserLookUpNotFound_thenReturn400() {
        when(mockUserLookUpService.userLookUp(emailAddress)).thenThrow(new EmailNotFoundException("Email not found"));
        Assertions.assertThrows(EmailNotFoundException.class, ()-> mockUserLookUpService.userLookUp(emailAddress));
    }

    @Test
    void whenIsEmailVerifyValidInput_thenReturn200() throws Exception {
        when(mockUserLookUpService.isEmailVerify(emailAddress)).thenReturn(userRegister);
        mockMvc.perform(post("/isEmailVerify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("isEmailVerify", emailAddress))
                .andExpect(status().isOk());
    }

    @Test
    void whenVerifyAccessCodeValidInput_thenReturn200() throws Exception{
        when(mockUserLookUpService.verifyAccessCode(emailAddress,accessCode)).thenReturn(userRegister);
        mockMvc.perform(get("/verifyAccessCode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("emailAddress", emailAddress)
                        .param("accessCode", String.valueOf(accessCode)))
                .andExpect(status().isOk());
    }

    @Test
    void whenVerifyAccessCodeNotFound_thenReturn400() throws Exception{
        when(mockUserLookUpService.verifyAccessCode(emailAddress,accessCode)).thenThrow(new EmailNotFoundException("Email not found"));
        mockMvc.perform(get("/verifyAccessCode")
                        .param("emailAddress",emailAddress)
                        .param("accessCode", String.valueOf(accessCode)))
                .andExpect(status().isNotFound());
    }
}
