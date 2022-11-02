package com.user.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLogIn{

    @NotEmpty
    @Email
    private String emailAddress;

    @NotEmpty
    @Size(min = 8, message = "must be minimum 8 length")
    private String userPassword;
}
