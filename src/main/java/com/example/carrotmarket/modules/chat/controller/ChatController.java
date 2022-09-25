package com.example.carrotmarket.modules.chat.controller;

import com.example.carrotmarket.common.ResponseDto;
import com.example.carrotmarket.config.auth.PrincipalDetails;
import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.modules.chat.domain.dto.ChatRequestDto;
import com.example.carrotmarket.modules.chat.domain.dto.ChatRoomDto;
import com.example.carrotmarket.modules.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {


    @Autowired
    private SimpMessagingTemplate messageTemplate;

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

    @GetMapping("/room/{roomIdx}")
    public ResponseEntity<?> roomDetail(Authentication authentication, @PathVariable @NotNull Long roomIdx){
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        ChatRoomDto roomDto = chatService.roomDetail(details.getUser(),roomIdx);
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.CHAT_ROOM_DETAIL_SUCCESS, roomDto), HttpStatus.OK);
    }

    // Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
    // 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.
    // 프론트에서 첫 채팅메시지를 받을 때 방이 없다면 그 때 http 요청을 해서 방을 받아오는 도중 두번째메시지가 온다면 꼬일위험 many! => room 리턴
    @MessageMapping("/chat.send") // 요청 엔드포인트 /app/chat.send
    @SendTo("/queue/pub")
    public void sendMessage(@Payload ChatRequestDto message, Authentication authentication) {
        /*
         * 특정 유저에게만 보내기!
         * 구독할 때는 /user/topic/pub
         * convertAndSendToUser 는 principal 의 username으로 보내지기 때문에
         * websocket의 principal username을 id로 설정해줌.(DB insert 시 userIdx를 알아야 하니...)
         */

        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();

        ChatRoomDto chat = chatService.sendMessage(message, details.getUser());

        messageTemplate.convertAndSendToUser(chat.getProduct().getUser().getUsername(), "/queue/pub", chat);
        messageTemplate.convertAndSendToUser(chat.getSender().getUsername(), "/queue/pub", chat);

    }

}
