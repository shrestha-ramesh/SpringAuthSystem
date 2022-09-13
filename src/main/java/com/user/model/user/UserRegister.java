package com.user.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserRegister {

    @Id
    @NotEmpty
    @Email
    private String emailAddress;

    @NotEmpty
    @Size(min = 8, message = "must be min 8 length")
    private String userPassword;

    @NotEmpty
    private String userName;

    @JsonIgnore
    private int accessCode;
    @JsonIgnore
    private boolean isEmailVerify;
    @JsonIgnore
    private String token;
}
