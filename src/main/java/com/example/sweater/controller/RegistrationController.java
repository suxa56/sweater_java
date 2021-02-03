package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

//      Современный вариант без @Autowired
//    private final UserService userService;
//
//    public UserService(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

//    Регистрация нового пользователся
    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
//        Если пользователь существует, выдается соответствующее сообщение
//        И возвращается на страницу регистрации
        if (!userService.addUser(user)) {
            model.put("message", "User exists");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

//        Проверяет код активации пользователя, если есть код, то выводит соответствующее сообщение
//        Если же нету кода (не пришел или уже был активирован), то выводит соответствующее сообщение
        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found");
        }

        return "login";
    }
}
