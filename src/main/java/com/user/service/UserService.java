package com.user.service;

import com.user.rabbitmq.MQConfig;
import com.user.model.user.UserLogIn;
import com.user.dto.UserLogInDTO;
import com.user.model.user.UserRegister;
import com.user.exception.EmailExistException;
import com.user.exception.EmailNotFoundException;
import com.user.exception.EmailNotVerifyException;
import com.user.exception.EmailPasswordException;
import com.user.repository.UserRepository;
import com.user.utils.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Function;

@Transactional
@Service
public class UserService {

    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    Authentication authentication;

    Function<String, UserRegister> getUserRegister = (gur)->{
        UserRegister userRegister = userRepository.findByEmailAddress(gur);
        return userRegister;
    };

    public UserService(UserRepository userRepository,JwtTokenUtil jwtTokenUtil){
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public UserRegister userRegister(UserRegister userRegister){
        UserRegister userRegister1 = getUserRegister.apply(userRegister.getEmailAddress());
        if(userRegister1 != null){
            throw new EmailExistException();
        }else {
            String newHash = bCryptPasswordEncoder.encode(userRegister.getUserPassword());
            userRegister.setUserPassword(newHash);
            userRepository.save(userRegister);

            template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, userRegister);

        }
        return userRegister;
    }
    public UserLogInDTO userLogIn(UserLogIn userLogIn) {
        UserRegister userRegister = getUserRegister.apply(userLogIn.getEmailAddress());
        if (userRegister == null) {
            throw new EmailNotFoundException();
        }
        if (!(bCryptPasswordEncoder.matches(userLogIn.getUserPassword(), userRegister.getUserPassword()))) {
            throw new EmailPasswordException();
        }
        if (!userRegister.isEmailVerify()) {
            throw new EmailNotVerifyException();
        }
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogIn.getEmailAddress(), userLogIn.getUserPassword()));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (BadCredentialsException e){
            throw new EmailNotFoundException();
        }
        String JWT_Token = jwtTokenUtil.generateToken(userLogIn);
        System.out.println(JWT_Token);
        userRepository.saveToken(JWT_Token, userLogIn.getEmailAddress());
        String DecrptJWT = jwtTokenUtil.getUsernameFromToken(JWT_Token);
        UserLogInDTO userLogInDTO = modelMapper.map(userRegister, UserLogInDTO.class);
        return userLogInDTO;
    }

    public HttpStatus userLogOut (String token){
        String userEmail = jwtTokenUtil.getUsernameFromToken(token);
        if(userEmail==null){
            return HttpStatus.NOT_FOUND;
        }
        UserRegister user = getUserRegister.apply(userEmail);
        if(user == null){
            return HttpStatus.UNAUTHORIZED;
        }
        if(!user.getToken().equals(token)){
            return HttpStatus.UNAUTHORIZED;
        }
        userRepository.saveToken(null, userEmail);
        return  HttpStatus.OK;
    }
}
