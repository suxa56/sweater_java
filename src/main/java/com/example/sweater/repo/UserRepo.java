package com.example.sweater.repo;

import com.example.sweater.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

//    Поиск пользователей по имени
    User findByUsername(String username);
//      Поиск по коду активации
    User findByActivationCode(String code);
}
