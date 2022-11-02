package com.user.model.authority;

import com.user.model.user.UserRegister;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    @ManyToOne
    @JoinColumn(name = "user_register_email_address")
    private UserRegister userRegister;
}
