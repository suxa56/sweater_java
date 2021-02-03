package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

//      Современный вариант без @Autowired
//    private final UserService userService;
//
//    public UserService(UserService userService) {
//        this.userService = userService;
//    }

//    Есть доступ только для пользователей с ролью ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

//    Есть доступ только для пользователей с ролью ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }
//    Есть доступ только для пользователей с ролью ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
//    Редактирование пользователя, его имя и роль
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

//    Мэппинг для страницы профиля
    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
         model.addAttribute("username", user.getUsername());
         model.addAttribute("email", user.getEmail());

        return "profile";
    }

//    Мэппинг для обновления профиля
    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }
}
