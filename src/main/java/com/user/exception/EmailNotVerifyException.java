package com.user.exception;

public class EmailNotVerifyException extends RuntimeException{
    public EmailNotVerifyException(String message){
        super(message);
    }
}
