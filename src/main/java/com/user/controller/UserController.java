package com.user.controller;

import com.user.model.user.UserLogIn;
import com.user.dto.UserLogInDTO;
import com.user.model.user.UserRegister;
import com.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/userRegister")
    public ResponseEntity<String> userRegister(@Valid @RequestBody UserRegister userRegister){
        log.info("UserRegister started");
        UserRegister userRegister1 = userService.userRegister(userRegister);
        log.info("UserRegister finished");
        return ResponseEntity.status(HttpStatus.CREATED).body("Account has been created");
    }
    @PostMapping("/userLogIn")
    public UserLogInDTO userLogIn(@Valid @RequestBody UserLogIn userLogIn){
        log.info("UserLogIn started");
        UserLogInDTO userLogInDTO = userService.userLogIn(userLogIn);
        log.info("UserLogIn finished");
        return userLogInDTO;
    }

    @GetMapping("/userLogOut")
    public ResponseEntity<HttpStatus> userLogOut(@RequestHeader("Authorization") String bearerToken){
        log.info("UserLogOut started");
        if(bearerToken==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        bearerToken = bearerToken.replace("Bearer ", "");
        HttpStatus status = userService.userLogOut(bearerToken);
        log.info("UserLogOut finished");
        return new ResponseEntity<>(status);
    }
}
