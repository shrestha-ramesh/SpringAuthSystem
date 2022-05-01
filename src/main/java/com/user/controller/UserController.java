package com.user.controller;

import com.user.model.UserLogIn;
import com.user.model.UserLogInDTO;
import com.user.model.UserRegister;
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
    public ResponseEntity<UserRegister> userRegister(@Valid @RequestBody UserRegister userRegister){
        UserRegister userRegister1 = userService.userRegister(userRegister);
        return new ResponseEntity<UserRegister>(HttpStatus.OK);
    }
    @PostMapping("/userLogIn")
    public ResponseEntity<UserLogInDTO> userLogIn(@Valid @RequestBody UserLogIn userLogIn){
        UserLogInDTO userLogInDTO = userService.userLogIn(userLogIn);
        return new ResponseEntity<UserLogInDTO>(userLogInDTO, HttpStatus.OK);
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
