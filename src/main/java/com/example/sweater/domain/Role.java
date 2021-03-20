package com.example.sweater.domain;

import org.springframework.security.core.GrantedAuthority;

//Роли
public enum Role implements GrantedAuthority {
    USER, ADMIN;


    @Override
    public String getAuthority() {
//        Строковое представление значений
        return name();
    }
}
