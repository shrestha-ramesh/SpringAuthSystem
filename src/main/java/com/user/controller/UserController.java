package com.user.controller;

import com.user.model.user.UserLogIn;
import com.user.dto.UserLogInDTO;
import com.user.model.user.UserRegister;
import com.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/userRegister")
    public ResponseEntity<String> userRegister(@Valid @RequestBody UserRegister userRegister){
        UserRegister userRegister1 = userService.userRegister(userRegister);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account has been created");
    }
    @PostMapping("/userLogIn")
    public UserLogInDTO userLogIn(@Valid @RequestBody UserLogIn userLogIn){
        UserLogInDTO userLogInDTO = userService.userLogIn(userLogIn);
        return userLogInDTO;
    }

    @GetMapping("/userLogOut")
    public ResponseEntity<HttpStatus> userLogOut(@RequestHeader("Authorization") String bearerToken){
        if(bearerToken==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        bearerToken = bearerToken.replace("Bearer ", "");
        HttpStatus status = userService.userLogOut(bearerToken);
        return new ResponseEntity<>(status);
    }
}
