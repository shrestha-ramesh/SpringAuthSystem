package com.user.security;

import com.user.model.authority.Authority;
import com.user.model.user.UserRegister;
import com.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomAuthenticationProvider(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserRegister userRegister = userRepository.findByEmailAddress(email);
        if(userRegister == null){
            throw new UsernameNotFoundException("User not found");
        }
        if(bCryptPasswordEncoder.matches(password, userRegister.getUserPassword())){
            return new UsernamePasswordAuthenticationToken(email, password, getAuthorities(userRegister.getAuthoritySet()));
        }else {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }
    private Set<SimpleGrantedAuthority> getAuthorities(Set<Authority> authorities){
        Set<SimpleGrantedAuthority> list = new HashSet<>();
        for(Authority auth: authorities){
            list.add(new SimpleGrantedAuthority(auth.getAuthority()));
        }
        return list;
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
