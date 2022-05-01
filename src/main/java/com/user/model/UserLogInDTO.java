package com.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLogInDTO {
    private String emailAddress;
    private String userName;
    private boolean isEmailVerify;
    private String token;
}
