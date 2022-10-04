package com.user.security;

import com.user.model.user.UserRegister;
import com.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        System.out.println("This is CustomUserDetailService");
        System.out.println(emailAddress);
        UserRegister userRegister = userRepository.findByEmailAddress(emailAddress);
        System.out.println(userRegister);
        if (userRegister == null){
            return (UserDetails) new UsernameNotFoundException("User not found");
        }
        System.out.println("This is after email print out");
        return new org.springframework.security.core.userdetails.User(userRegister.getEmailAddress(), userRegister.getUserPassword(), new ArrayList<>());
    }
}
