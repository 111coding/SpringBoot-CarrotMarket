package com.example.carrotmarket.modules.chat.domain.dto;

import com.example.carrotmarket.enums.ChatMessageType;
import com.example.carrotmarket.modules.chat.domain.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private Long idx;

    private ChatMessageType messageType;

    private String content;

    private Timestamp createAt;

    public ChatMessageDto(ChatMessage chat){
        this.idx = chat.getIdx();
        this.messageType = chat.getMessageType();
        this.content = chat.getContent();
        this.createAt = chat.getCreateAt();
    }
}
