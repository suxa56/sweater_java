package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserRepo userRepo;
//      Современный вариант без @Autowired
//    private final UserRepo userRepo;
//
//    public UserService(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user) {
//        Создается новая переменная, которой присваивается
//        пользователь из базы данных совпадающий с получаемым в форме регистрации
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

//        Если пользователь новый(нету совпадений), то ему задается активность, дается роль,
//        сохраняется через репозиторий и перенапрявляется на страницу входа
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
//        Добавляет поле с рандомным кодом активации
//        При совпадении почта будет подтверждена
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);

//        Если поле почты не пустой, то отправляет пользователю почту
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

//            отправляет письмо на почту(user,getEmail()), с темой "Activation", и текстом message
            mailSender.send(user.getEmail(), "Activation", message);
        }
        return true;
    }

    public boolean activateUser(String code) {
//        Ищет пользователя по коду активации
        User user = userRepo.findByActivationCode(code);

//        Если нету такого, то возвращает null
        if (user == null) {
            return false;
        }
//        Если же есть, то анулирует код, чтобы не мог повторно вктивировать
        user.setActivationCode(null);

//        Сохраняет в базу данных и возвращает true
        userRepo.save(user);

        return true;
    }
}
