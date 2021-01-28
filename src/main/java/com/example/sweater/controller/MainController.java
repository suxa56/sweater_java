package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageRepo messageRepo;
//      Современный вариант без @Autowired
//    private final MessageRepo messageRepo;
//
//    public UserService(MessageRepo messageRepo) {
//        this.messageRepo = messageRepo;
//    }

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

//    Главная страница со всеми сообщениями
    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

//    Добавление новых сообщений
    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model) {
//        Создает объект класса Message и сохраняет его через метод save
        Message message = new Message(text, tag, user);
        messageRepo.save(message);

//        И сразу, без перезагрузок выводит сообщения
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);

        return "main";
    }

//    @PostMapping("filter") связан с тегом form и атрибутом action
//    Метод ищет сообщения по String filter, который передается в тэге input, атрибуте name
    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;
//        Если фильтр не пустой, то возвращается результат
//        Если фильтр пустой, то он возвращает все сообщения
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }
        model.put("messages", messages);
        return "main";
    }

}
