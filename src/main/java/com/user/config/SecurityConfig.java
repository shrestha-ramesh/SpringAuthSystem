package com.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/isEmailVerify").authenticated()
                .antMatchers("/verifyAccessCode").authenticated()
                .antMatchers("/userRegister").permitAll()
                .antMatchers("/userLogIn").permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}
