package com.user.model.exception;

public class EmailExistException extends RuntimeException{

    public EmailExistException(String message){
        super(message);
    }
}
