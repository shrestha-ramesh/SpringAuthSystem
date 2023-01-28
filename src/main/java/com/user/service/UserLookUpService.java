package com.user.service;


import com.user.model.user.UserRegister;
import com.user.exception.EmailNotFoundException;
import com.user.repository.UserLookUpRepository;
import com.user.utils.SendEmail;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;
import java.util.function.Predicate;

@Transactional
@Service

public class UserLookUpService {

    private UserLookUpRepository userLookUpRepository;
    private SendEmail sendEmail;

    Predicate<UserRegister> checkUserRegister =(userRegister)->{
        if(userRegister == null){
            return true;
        }else {
            return false;
        }
    };

    public UserLookUpService(UserLookUpRepository userLookUpRepository, SendEmail sendEmail){
        this.userLookUpRepository = userLookUpRepository;
        this.sendEmail = sendEmail;
    }
    public UserRegister userLookUp(String emailAddress){
        UserRegister userRegister = userLookUpRepository.findByEmailAddress(emailAddress);
        if(checkUserRegister.test(userRegister)){
            throw new EmailNotFoundException();
        }
        return userRegister;
    }

    public UserRegister isEmailVerify(String isEmailVerify) {
        UserRegister userRegister = userLookUpRepository.isEmailVerify(isEmailVerify);
        if(checkUserRegister.test(userRegister)){
            throw new EmailNotFoundException(isEmailVerify);
        } else {
            Random r = new Random( System.currentTimeMillis() );
            int accessCode = 10000 + r.nextInt(20000);
            userLookUpRepository.saveAccessCode(accessCode, isEmailVerify);
            String accessCodeString = String.valueOf(accessCode);
            sendEmail.emailSend(isEmailVerify, "AccessCode", accessCodeString);
        }
        return userRegister;
    }

    public UserRegister verifyAccessCode(String emailAddress, int accessCode) {
        UserRegister userRegister = userLookUpRepository.verifyAccessCode(emailAddress, accessCode);
        if (checkUserRegister.test(userRegister)){
            throw new EmailNotFoundException();
        }else {
            userLookUpRepository.saveIsVerify(true, emailAddress);
        }
        return userRegister;
    }

    public HttpStatus forgetPassword(String emailAddress, Integer accessCode, String userPassword) {
        UserRegister userRegister = userLookUpRepository.isEmailVerify(emailAddress);
        if (checkUserRegister.test(userRegister))
            throw new EmailNotFoundException();

        if(!emailAddress.isEmpty() && accessCode != null){
            if(userRegister.getAccessCode() != accessCode){
                return HttpStatus.UNAUTHORIZED;
            }else if (userPassword == null || userPassword == ""){
                return HttpStatus.NOT_ACCEPTABLE;
            }else if (userPassword.length() >= 8){
                return HttpStatus.NOT_ACCEPTABLE;
            }else {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                userPassword = bCryptPasswordEncoder.encode(userPassword);
                userLookUpRepository.forgetPassword(userPassword, emailAddress);
            }
        }else {
            Random r = new Random( System.currentTimeMillis() );
            accessCode = 10000 + r.nextInt(20000);
            userLookUpRepository.saveAccessCode(accessCode, emailAddress);
            String accessCodeString = String.valueOf(accessCode);
            sendEmail.emailSend(emailAddress, "AccessCode", accessCodeString);

        }
        return HttpStatus.OK;
    }
}
