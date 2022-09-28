package com.user.controller;

import com.user.model.user.UserRegister;
import com.user.service.UserLookUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Check there is email exist or not")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email Found",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Email not Found",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/userLookUp/{userLookUp}")
    public ResponseEntity<String> userLookUp(@PathVariable("userLookUp") String emailAddress){
        log.info("UserLookUp started");
       UserRegister userRegister = userLookUpService.userLookUp(emailAddress);
       log.info("UserLookUp finished");
       return ResponseEntity.status(HttpStatus.OK).body("Email Found");

    }
    @GetMapping("/dashboard")
    public String getDashboard(){
        return "This is dashboard";
    }
    @GetMapping("/home")
    public String getHome(){
        return "This is Home";
    }
    @Operation(summary = "Check to see email is verify")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email is verify",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Email not verify",
                    content = {@Content(mediaType = "application/json")})
    })
    @PostMapping("/isEmailVerify")
    public ResponseEntity<String> isEmailVerify(@RequestParam("email") String email){
        log.info("isEmailVerify started");
        UserRegister userRegister = userLookUpService.isEmailVerify(email);
        log.info("isEmailVerify finished");
        return ResponseEntity.status(HttpStatus.OK).body("Email Verify");
    }
    @Operation(summary = "This endpoints verify the access code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access code verify",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not able to verify access code",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/verifyAccessCode")
    public ResponseEntity<String> verifyAccessCode(@RequestParam("emailAddress") String emailAddress, @RequestParam("accessCode") int accessCode){
        log.info("verifyAccessCode started");
        UserRegister userRegister = userLookUpService.verifyAccessCode(emailAddress, accessCode);
        log.info("verifyAccessCode finished");
        return ResponseEntity.status(HttpStatus.OK).body("AccessCode is Verify");
    }
    @Operation(summary = "Reset the password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "able to reset password",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "access code does not match"),
            @ApiResponse(responseCode = "406", description = "password should not empty or null"),
            @ApiResponse(responseCode = "406", description = "password length at least 8 character")
    })
    @PostMapping("/forget/password")
    public ResponseEntity<HttpStatus> forgetPassword(@RequestParam("emailAddress") String emailAddress, @RequestParam(value = "accessCode", required = false) Integer accessCode,
                                         @RequestParam(value = "userPassword", required = false) String userPassword){
        log.info("Forget password started");
        HttpStatus status = userLookUpService.forgetPassword(emailAddress, accessCode, userPassword);
        log.info("Forget password finished");
        return new ResponseEntity(status);
    }
}
