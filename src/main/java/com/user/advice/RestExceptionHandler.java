package com.user.advice;

import com.user.exception.*;
import com.user.model.error.ErrorInfo;
import com.user.model.error.ErrorResponseType;
import com.user.model.error.Globals;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler {

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo handleEmailNotFoundException(EmailNotFoundException emailNotFoundException){
        ArrayList<String> error = new ArrayList<>();
        error.add(ErrorResponseType.ERROR.toString());

        if(emailNotFoundException.getMessage() == null){
            return ErrorInfo.builder()
                    .message(Globals.EMAIL_NOT_FOUND)
                    .type(ErrorResponseType.ERROR)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .errors(error)
                    .build();
        }

        return ErrorInfo.builder()
                .message(Globals.EMAIL_NOT_FOUND+" "+emailNotFoundException.getMessage())
                .type(ErrorResponseType.ERROR)
                .httpStatus(HttpStatus.NOT_FOUND)
                .errors(error)
                .build();
    }
    @ExceptionHandler(EmailExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleEmailExistException(EmailExistException emailExistException){
        ArrayList<String> error = new ArrayList<>();
        error.add(ErrorResponseType.ERROR.toString());

        return ErrorInfo.builder()
                .message(Globals.EMAIL_EXIST)
                .type(ErrorResponseType.INVALID_INPUT)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errors(error)
                .build();
    }
    @ExceptionHandler(EmailPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorInfo handleEmailPasswordException(EmailPasswordException emailPasswordException){
        ArrayList<String> error = new ArrayList<>();
        error.add(ErrorResponseType.INVALID_INPUT.toString());

        return ErrorInfo.builder()
                .message(Globals.PASSWORD_NOT_MATCH)
                .type(ErrorResponseType.INVALID_INPUT)
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .errors(error)
                .build();
    }
    @ExceptionHandler(EmailNotVerifyException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorInfo handlerEmailNotVerifyException(EmailNotVerifyException emailNotVerifyException){
        ArrayList<String> error = new ArrayList<>();
        error.add(ErrorResponseType.ERROR.toString());

        return ErrorInfo.builder()
                .message(Globals.EMAIL_NOT_VERIFY)
                .type(ErrorResponseType.ERROR)
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .errors(error)
                .build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo validationErrorResponse(MethodArgumentNotValidException e){
        List<String> collect = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField()+" "+fieldError.getDefaultMessage()).collect(Collectors.toList());
        return ErrorInfo.builder()
                .message(Globals.VALIDATION)
                .type(ErrorResponseType.INVALID_INPUT)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errors(collect)
                .build();
    }
}
