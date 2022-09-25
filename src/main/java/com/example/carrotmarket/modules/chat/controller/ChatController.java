package com.example.carrotmarket.modules.chat.controller;

import com.example.carrotmarket.common.ResponseDto;
import com.example.carrotmarket.config.auth.PrincipalDetails;
import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.modules.chat.domain.dto.ChatRoomDto;
import com.example.carrotmarket.modules.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {


    @Autowired
    private ChatService chatService;


    @PostMapping("/room/make/{productIdx}")
    public ResponseEntity<?> makeRoom(Authentication authentication, @PathVariable @NotNull Long productIdx){
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        ChatRoomDto roomDto = chatService.makeRoom(details.getUser().getIdx(), productIdx);
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.CHAT_ROOM_MAKE_SUCCESS, roomDto), HttpStatus.CREATED);
    }

    @GetMapping("/room/list")
    public ResponseEntity<?> roomList(Authentication authentication){
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        List<ChatRoomDto> msgList = chatService.roomList(details.getUser());
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.CHAT_ROOM_LIST_SUCCESS, msgList), HttpStatus.OK);
    }

}
