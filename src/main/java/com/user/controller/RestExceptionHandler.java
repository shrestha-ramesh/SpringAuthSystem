package com.user.controller;

import com.user.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleEmailNotFoundException(EmailNotFoundException emailNotFoundException){
        return new ResponseEntity<>(new ErrorInfo(emailNotFoundException.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<ErrorInfo> handleEmailExistException(EmailExistException emailExistException){
        return new ResponseEntity<ErrorInfo>(new ErrorInfo(emailExistException.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailPasswordException.class)
    public ResponseEntity<ErrorInfo> handleEmailPasswordException(EmailPasswordException emailPasswordException){
        return new ResponseEntity<ErrorInfo>(new ErrorInfo(emailPasswordException.getMessage(), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(EmailNotVerifyException.class)
    public ResponseEntity<ErrorInfo> handlerEmailNotVerifyException(EmailNotVerifyException emailNotVerifyException){
        return new ResponseEntity<ErrorInfo>(new ErrorInfo(emailNotVerifyException.getMessage(), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }
}
