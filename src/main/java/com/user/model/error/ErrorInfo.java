package com.user.model.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorInfo {

    private String message;
    private HttpStatus httpStatus;
    private List<String> errors;
    private ErrorResponseType type;
}
