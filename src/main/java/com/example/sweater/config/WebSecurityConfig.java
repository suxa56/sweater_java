package com.example.sweater.config;

import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//Анотация для @PreAuthorize("hasAuthority('ADMIN')")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
//      Современный вариант без @Autowired
//    private final UserService userService;
//
//    public UserService(UserService userService) {
//        this.userService = userService;
//    }

    @Autowired
    private PasswordEncoder passwordEncoder;
//      Современный вариант без @Autowired
//    private final PasswordEncoder passwordEncoder;
//
//    public PasswordEncoder(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

//    Метод шифрования паролей

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                Позволяет входить по данным адресам всем посетителям
//                UPDATE прогружает стили для всех
                    .antMatchers("/", "/registration", "/static/**", "/activate/*").permitAll()
//                Для любых други запросов нужна авторизация
                    .anyRequest().authenticated()
                .and()
//                Форма логина находится по такому адресу и доступен для всех
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .rememberMe()
                .and()
//                Логаут доступен для всех
                    .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}