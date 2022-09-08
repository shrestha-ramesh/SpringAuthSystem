package com.user.controller;

import com.user.model.user.UserRegister;
import com.user.service.UserLookUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserLookUpController {

    private UserLookUpService userLookUpService;

    public UserLookUpController(UserLookUpService userLookUpService){
        this.userLookUpService = userLookUpService;
    }

    @GetMapping("/userLookUp/{userLookUp}")
    public ResponseEntity<String> userLookUp(@PathVariable("userLookUp") String emailAddress){
       UserRegister userRegister = userLookUpService.userLookUp(emailAddress);
       return ResponseEntity.status(HttpStatus.OK).body("Email Found");

    }

    @PostMapping("/isEmailVerify")
    public ResponseEntity<String> isEmailVerify(@RequestParam("email") String email){
        UserRegister userRegister = userLookUpService.isEmailVerify(email);
        return ResponseEntity.status(HttpStatus.OK).body("Email Verify");
    }

    @GetMapping("/verifyAccessCode")
    public ResponseEntity<String> verifyAccessCode(@RequestParam("emailAddress") String emailAddress, @RequestParam("accessCode") int accessCode){
        UserRegister userRegister = userLookUpService.verifyAccessCode(emailAddress, accessCode);
        return ResponseEntity.status(HttpStatus.OK).body("AccessCode is Verify");
    }
    @PostMapping("/forget/password")
    public ResponseEntity<HttpStatus> forgetPassword(@RequestParam("emailAddress") String emailAddress, @RequestParam(value = "accessCode", required = false) Integer accessCode,
                                         @RequestParam(value = "userPassword", required = false) String userPassword){
        HttpStatus status = userLookUpService.forgetPassword(emailAddress, accessCode, userPassword);
        return new ResponseEntity(status);
    }
}
