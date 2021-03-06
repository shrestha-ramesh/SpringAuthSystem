package com.user.service;


import com.user.model.UserRegister;
import com.user.model.exception.EmailNotFoundException;
import com.user.repository.UserLookUpRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Transactional
@Service
public class UserLookUpService {

    private UserLookUpRepository userLookUpRepository;
    private SendEmail sendEmail;

    public UserLookUpService(UserLookUpRepository userLookUpRepository, SendEmail sendEmail){
        this.userLookUpRepository = userLookUpRepository;
        this.sendEmail = sendEmail;
    }
    public UserRegister userLookUp(String emailAddress){
        UserRegister userRegister = userLookUpRepository.findByEmailAddress(emailAddress);
        if(userRegister == null){
            throw new EmailNotFoundException("Email Not Found");
        }
        return userRegister;
    }

    public UserRegister isEmailVerify(String isEmailVerify) {
        UserRegister userRegister = userLookUpRepository.isEmailVerify(isEmailVerify);
        if(userRegister == null){
            throw new EmailNotFoundException("Email not Found "+isEmailVerify);
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
        if (userRegister == null){
            throw new EmailNotFoundException("Not Found");
        }else {
            userLookUpRepository.saveIsVerify(true, emailAddress);
        }
        return userRegister;
    }

    public HttpStatus forgetPassword(String emailAddress, Integer accessCode, String userPassword) {
        UserRegister userRegister = userLookUpRepository.isEmailVerify(emailAddress);
        if (userRegister == null)
            throw new EmailNotFoundException("Email not found");

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
