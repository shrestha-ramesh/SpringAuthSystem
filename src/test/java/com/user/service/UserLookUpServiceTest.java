package com.user.service;

import com.user.model.UserRegister;
import com.user.model.exception.EmailNotFoundException;
import com.user.repository.UserLookUpRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserLookUpServiceTest {

    @MockBean
    private UserLookUpRepository mockUserLookUpRepository;

    @Autowired
    private UserLookUpService userLookUpService;

    String emailAddress = "shresthashare@gmail.com";
    UserRegister userRegister;

    @Test
    void whenUserLookUpEmailFound_thenReturnUserRegister() throws Exception{
        userRegister = UserRegister.builder().build();
        when(mockUserLookUpRepository.findByEmailAddress(emailAddress)).thenReturn(userRegister);
        UserRegister userRegister1 = userLookUpService.userLookUp(emailAddress);
        Assertions.assertEquals(userRegister, userRegister1);
    }

    @Test
    void whenUserLookUpEmailNotFound_thenReturnNotFound() throws Exception{
        userRegister =null;
        Assertions.assertThrows(EmailNotFoundException.class,()->userLookUpService.userLookUp(emailAddress));
    }

    @Test
    void whenIsEmailVerify_thenReturnUserRegister() throws Exception{
        userRegister = UserRegister.builder().build();
        when(mockUserLookUpRepository.isEmailVerify(emailAddress)).thenReturn(userRegister);
        UserRegister userRegister1 = userLookUpService.isEmailVerify(emailAddress);
        Assertions.assertEquals(userRegister, userRegister1);
    }

    @Test
    void whenIsEmailVerifyUserRegisterNull_thenReturnNotFound() throws Exception{
        userRegister =null;
        Assertions.assertThrows(EmailNotFoundException.class,()->userLookUpService.isEmailVerify(emailAddress));
    }

    @Test
    void whenVerifyAccessCodeUserRegisterNull_thenReturnNotFound() throws Exception{
        int accessCode=12345;
        Assertions.assertThrows(EmailNotFoundException.class,()->userLookUpService.verifyAccessCode(emailAddress,accessCode));
    }

    @Test
    void whenVerifyAccessCodeValidInput_thenReturnUserRegister() throws Exception{
        userRegister = UserRegister.builder().build();
        int accessCode = 12345;
        when(mockUserLookUpRepository.verifyAccessCode(emailAddress,accessCode)).thenReturn(userRegister);
        UserRegister userRegister1 = userLookUpService.verifyAccessCode(emailAddress, accessCode);
        Assertions.assertEquals(userRegister, userRegister1);
    }
}
