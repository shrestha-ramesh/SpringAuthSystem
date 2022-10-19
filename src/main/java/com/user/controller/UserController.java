package com.user.controller;

import com.user.model.user.UserLogIn;
import com.user.dto.UserLogInDTO;
import com.user.model.user.UserRegister;
import com.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;


@RestController
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "This is UserRegister store the data")
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "Save data, Account create",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Not available",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/home")
    public String getHome(){
        return "This is home";
    }
    @GetMapping("/profile")
    @PreAuthorize("hasRole('ADMIN')")
    public String getProfile(){
        return "This is profile";
    }
    @GetMapping("/dash")
    @PreAuthorize("hasRole('USER')")
    public String getDash(){
        return "This is dash";
    }
    @PostMapping("/userRegister")
    public ResponseEntity<String> userRegister(@Valid @RequestBody UserRegister userRegister){
        log.info("UserRegister started");
        UserRegister userRegister1 = userService.userRegister(userRegister);
        log.info("UserRegister finished");
        return ResponseEntity.status(HttpStatus.CREATED).body("Account has been created");
    }
    @Operation(summary = "This is authentication ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Able to log in to account return with token",
                    content={@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Wrong user and password",
                    content ={@Content(mediaType = "application/json")})
    })
    @PostMapping("/userLogIn")
    public UserLogInDTO userLogIn(@Valid @RequestBody UserLogIn userLogIn){
        log.info("UserLogIn started");
        UserLogInDTO userLogInDTO = userService.userLogIn(userLogIn);
        log.info("UserLogIn finished");
        return userLogInDTO;
    }
    @Operation(summary = "This will logout the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is logout",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "User is not authorized",
                    content = {@Content(mediaType = "application/json")})
    })
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
