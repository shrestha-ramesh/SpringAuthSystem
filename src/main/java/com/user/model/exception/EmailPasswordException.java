package com.user.model.exception;

public class EmailPasswordException extends RuntimeException{
    public EmailPasswordException(String message){
        super(message);
    }
}
