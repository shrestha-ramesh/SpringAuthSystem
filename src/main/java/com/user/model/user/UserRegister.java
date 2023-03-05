package com.user.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.model.authority.Authority;
import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userRegister")
    private Set<Authority> authoritySet;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int accessCode;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isEmailVerify;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String token;
}
