package com.example.carrotmarket.modules.chat.repository;

import com.example.carrotmarket.modules.chat.domain.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
