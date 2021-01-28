package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

//    Регистрация нового пользователся
    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
//        Создается новая переменная, которой присваивается
//        пользователь из базы данных совпадающий с получаемым в форме регистрации
        User userFromDb = userRepo.findByUsername(user.getUsername());

//        Если пользователь существует, выдается соответствующее сообщение
//        И возвращается на страницу регистрации
        if (userFromDb != null) {
            model.put("message", "User exists");
            return "registration";
        }

//        Если пользователь новый(нету совпадений), то ему задается активность, дается роль,
//        сохраняется через репозиторий и перенапрявляется на страницу входа
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
}
