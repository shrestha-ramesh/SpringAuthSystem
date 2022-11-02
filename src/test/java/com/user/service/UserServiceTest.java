package com.user.service;

import com.user.model.user.UserLogIn;
import com.user.dto.UserLogInDTO;
import com.user.model.user.UserRegister;
import com.user.exception.EmailExistException;
import com.user.exception.EmailNotFoundException;
import com.user.exception.EmailNotVerifyException;
import com.user.exception.EmailPasswordException;
import com.user.repository.UserRepository;
import com.user.utils.JwtTokenUtil;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;

    Authentication authentication;

    UserRegister userRegister;
    UserLogIn userLogIn;
    UserLogInDTO userLogInDTO;

    @BeforeEach
    void init(){
        userRegister = UserRegister.builder()
                .emailAddress("shresthashare@gmail.com")
                .userPassword("userPassword")
                .build();
        userLogIn = UserLogIn.builder()
                .emailAddress("shresthashare@gmail.com")
                .userPassword("userPassword")
                .build();
        userLogInDTO = UserLogInDTO.builder()
                .isEmailVerify(true)
                .emailAddress("shresthashare@gmail.com")
                .build();
    }

    @Test
    void whenAllValidInput_thenReturnUserRegister() throws Exception{
        when(mockUserRepository.findByEmailAddress(userRegister.getEmailAddress())).thenReturn(null);
        UserRegister userRegister1 = userService.userRegister(userRegister);
        Assertions.assertEquals(userRegister1,userRegister);
    }

    @Test
    void whenEmailExist_thenReturnEmailExist() throws Exception{
        when(mockUserRepository.findByEmailAddress(userRegister.getEmailAddress())).thenReturn(userRegister);
        Assertions.assertThrows(EmailExistException.class,()->userService.userRegister(userRegister));
    }

//    @Test
//    void whenAllValidInput_thenReturnUserLogInDTO() throws Exception{
//        userRegister.setUserPassword("userPassword");
//        userRegister.setEmailVerify(true);
//        when(mockUserRepository.userLogIn(userLogIn.getEmailAddress())).thenReturn(userRegister);
//        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogIn.getEmailAddress(), userLogIn.getUserPassword()))).thenReturn((Authentication) userLogIn);
//        UserLogInDTO userLogInDTO1 = userService.userLogIn(userLogIn);
//        Assertions.assertEquals(userLogInDTO, userLogInDTO1);
//    }

    @Test
    void whenUserLogInEmailNotFound_thenReturnEmailNotFound() throws Exception{
        userRegister = null;
        when(mockUserRepository.userLogIn(userLogIn.getEmailAddress())).thenReturn(userRegister);
        Assertions.assertThrows(EmailNotFoundException.class,()->userService.userLogIn(userLogIn));
    }

    @Test
    void whenUserLogInPasswordNotMatch_thenReturnPasswordNotMatch() throws Exception{
        userRegister.setUserPassword("");
        when(mockUserRepository.userLogIn(userLogIn.getEmailAddress())).thenReturn(userRegister);
        Assertions.assertThrows(EmailPasswordException.class,()->userService.userLogIn(userLogIn));
    }

    @Test
    void whenUserLogInEmailNotVerify() throws Exception{
        userRegister.setEmailVerify(false);
        userRegister.setUserPassword("$2a$10$ZvREh.u4QoKRAiOEuIqf0evMP5dB4uxpzPmMg9HlGOnnspG.28LHG");
        when(mockUserRepository.userLogIn(userLogIn.getEmailAddress())).thenReturn(userRegister);
        Assertions.assertThrows(EmailNotVerifyException.class,()->userService.userLogIn(userLogIn));
    }

//    @Test
//    void whenUserLogInTokenNotNull()throws Exception{
//        userRegister.setUserPassword("$2a$10$ZvREh.u4QoKRAiOEuIqf0evMP5dB4uxpzPmMg9HlGOnnspG.28LHG");
//        userRegister.setEmailVerify(true);
//        userRegister.setToken("ajsfdkjasdf");
//        userLogInDTO.setToken("ajsfdkjasdf");
//        when(mockUserRepository.userLogIn(userLogIn.getEmailAddress())).thenReturn(userRegister);
//        UserLogInDTO userLogInDTO1 = userService.userLogIn(userLogIn);
//        Assertions.assertEquals(userLogInDTO, userLogInDTO1);
//    }
}
