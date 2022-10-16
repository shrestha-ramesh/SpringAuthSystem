package com.user.security;

import com.user.model.user.UserRegister;
import com.user.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
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
        UserRegister userRegister = userRepository.findByEmailAddress(emailAddress);
        if (userRegister == null){
            throw new UsernameNotFoundException("User not found");
        }
        User user = new User(userRegister.getEmailAddress(), userRegister.getUserPassword(), new ArrayList<>());
        return user;
    }
}
