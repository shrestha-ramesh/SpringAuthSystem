package com.user.security;

import com.user.model.error.ErrorResponseType;
import com.user.userauthority.UserAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.user.userauthority.UserAuthority.ADMIN;
import static com.user.userauthority.UserAuthority.USER;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService){
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/userRegister","/userLogIn").permitAll()
                .antMatchers("/profile").hasRole(ADMIN)
                .antMatchers("/home").hasRole(USER)
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(customAuthenticationProvider);
    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        UserDetails userDetails = User.withUsername("ramesh").password("12345").authorities("admin").build();
//        UserDetails userDetails1 = User.withUsername("shrestha").password("12345").authorities("user").build();
//        userDetailsManager.createUser(userDetails);
//        userDetailsManager.createUser(userDetails1);
//        auth.userDetailsService(userDetailsManager);
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
