package com.user.controller;

import com.user.model.UserRegister;
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
    public ResponseEntity userLookUp(@PathVariable("userLookUp") String emailAddress){
       UserRegister userRegister = userLookUpService.userLookUp(emailAddress);
       return new ResponseEntity<UserRegister>(HttpStatus.OK);

    }

    @PostMapping("/isEmailVerify")
    public ResponseEntity isEmailVerify(@RequestParam("isEmailVerify") String isEmailVerify){
        UserRegister userRegister = userLookUpService.isEmailVerify(isEmailVerify);
        return new ResponseEntity<UserRegister>(HttpStatus.OK);
    }

    @GetMapping("/verifyAccessCode")
    public ResponseEntity<UserRegister> verifyAccessCode(@RequestParam("emailAddress") String emailAddress, @RequestParam("accessCode") int accessCode){
        UserRegister userRegister = userLookUpService.verifyAccessCode(emailAddress, accessCode);
        return new ResponseEntity<UserRegister>(HttpStatus.OK);
    }
}
