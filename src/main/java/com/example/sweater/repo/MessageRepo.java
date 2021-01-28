package com.example.sweater.repo;

import com.example.sweater.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

//    Поиск сообщений по тегам
    List<Message> findByTag(String tag);
}
