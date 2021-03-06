package com.user.service;

import com.user.model.UserLogIn;
import com.user.model.UserLogInDTO;
import com.user.model.UserRegister;
import com.user.model.exception.EmailExistException;
import com.user.model.exception.EmailNotFoundException;
import com.user.model.exception.EmailNotVerifyException;
import com.user.model.exception.EmailPasswordException;
import com.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class UserService {

    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;

    ModelMapper modelMapper = new ModelMapper();

    public UserService(UserRepository userRepository,JwtTokenUtil jwtTokenUtil){
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public UserRegister userRegister(UserRegister userRegister){
        UserRegister userRegister1 = userRepository.findByEmailAddress(userRegister.getEmailAddress());
        if(userRegister1 != null){
            throw new EmailExistException("Email Exist");
        }else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String newHash = bCryptPasswordEncoder.encode(userRegister.getUserPassword());
            userRegister.setUserPassword(newHash);
            userRepository.save(userRegister);
        }
        return userRegister;
    }
    public UserLogInDTO userLogIn(UserLogIn userLogIn) {
        UserRegister userRegister = userRepository.userLogIn(userLogIn.getEmailAddress());
        if (userRegister == null) {
            throw new EmailNotFoundException("Email Not Found ");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!(bCryptPasswordEncoder.matches(userLogIn.getUserPassword(), userRegister.getUserPassword()))) {
            throw new EmailPasswordException("Password not match");
        }
        if (!userRegister.isEmailVerify()) {
            throw new EmailNotVerifyException("Email Not Verify");
        }

        String JWT_Token = jwtTokenUtil.generateToken(userLogIn);
        System.out.println(JWT_Token);
        userRepository.saveToken(JWT_Token, userLogIn.getEmailAddress());
        String DecrptJWT = jwtTokenUtil.getUsernameFromToken(JWT_Token);
        UserLogInDTO userLogInDTO = UserLogInDTO.builder()
                .userName(userRegister.getUserName())
                .token(JWT_Token)
                .emailAddress(userRegister.getEmailAddress())
                .isEmailVerify(userRegister.isEmailVerify())
                .build();


        return userLogInDTO;
    }

    public HttpStatus userLogOut (String token){
        String userEmail = jwtTokenUtil.getUsernameFromToken(token);
        if(userEmail==null){
            return HttpStatus.NOT_FOUND;
        }
        UserRegister user = userRepository.findByEmailAddress(userEmail);
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
