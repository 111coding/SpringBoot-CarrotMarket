package com.example.carrotmarket.modules.chat.domain.dto;

import com.example.carrotmarket.modules.chat.domain.entity.ChatMessage;
import com.example.carrotmarket.modules.chat.domain.entity.ChatRoom;
import com.example.carrotmarket.modules.user.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {

    private Long roomIdx;

    private ChatRoomProductDto product;

    private UserDto sender;

    private List<ChatMessageDto> messages;

    private Timestamp createAt;

    public ChatRoomDto(ChatRoom room){
        this.roomIdx = room.getIdx();
        this.product = new ChatRoomProductDto(room.getProduct());
        this.sender = new UserDto(room.getSender());
        if(room.getMessages() !=null){
            this.messages = room.getMessages().stream().map(ChatMessageDto::new).collect(Collectors.toList());
        }
        this.createAt = room.getCreateAt();
    }

    public ChatRoomDto(ChatRoom room, ChatMessage lastMessage){
        this.roomIdx = room.getIdx();
        this.product = new ChatRoomProductDto(room.getProduct());
        this.sender = new UserDto(room.getSender());
        this.messages = List.of(new ChatMessageDto(lastMessage));
        this.createAt = room.getCreateAt();
    }

}
