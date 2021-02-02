package com.example.sweater.repo;

import com.example.sweater.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

//    Поиск пользователей по имени
    User findByUsername(String username);

    User findByActivationCode(String code);
}
