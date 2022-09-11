package com.user.controller;

import com.user.model.user.UserRegister;
import com.user.service.UserLookUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class UserLookUpController {

    private UserLookUpService userLookUpService;

    public UserLookUpController(UserLookUpService userLookUpService){
        this.userLookUpService = userLookUpService;
    }

    @GetMapping("/userLookUp/{userLookUp}")
    public ResponseEntity<String> userLookUp(@PathVariable("userLookUp") String emailAddress){
        log.info("UserLookUp started");
       UserRegister userRegister = userLookUpService.userLookUp(emailAddress);
       log.info("UserLookUp finished");
       return ResponseEntity.status(HttpStatus.OK).body("Email Found");

    }

    @PostMapping("/isEmailVerify")
    public ResponseEntity<String> isEmailVerify(@RequestParam("email") String email){
        log.info("isEmailVerify started");
        UserRegister userRegister = userLookUpService.isEmailVerify(email);
        log.info("isEmailVerify finished");
        return ResponseEntity.status(HttpStatus.OK).body("Email Verify");
    }

    @GetMapping("/verifyAccessCode")
    public ResponseEntity<String> verifyAccessCode(@RequestParam("emailAddress") String emailAddress, @RequestParam("accessCode") int accessCode){
        log.info("verifyAccessCode started");
        UserRegister userRegister = userLookUpService.verifyAccessCode(emailAddress, accessCode);
        log.info("verifyAccessCode finished");
        return ResponseEntity.status(HttpStatus.OK).body("AccessCode is Verify");
    }
    @PostMapping("/forget/password")
    public ResponseEntity<HttpStatus> forgetPassword(@RequestParam("emailAddress") String emailAddress, @RequestParam(value = "accessCode", required = false) Integer accessCode,
                                         @RequestParam(value = "userPassword", required = false) String userPassword){
        log.info("Forget password started");
        HttpStatus status = userLookUpService.forgetPassword(emailAddress, accessCode, userPassword);
        log.info("Forget password finished");
        return new ResponseEntity(status);
    }
}
