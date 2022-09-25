package com.example.carrotmarket.modules.chat.service;

import com.example.carrotmarket.enums.ChatMessageType;
import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.handler.exception.CustomApiException;
import com.example.carrotmarket.modules.chat.domain.dto.ChatRequestDto;
import com.example.carrotmarket.modules.chat.domain.dto.ChatRoomDto;
import com.example.carrotmarket.modules.chat.domain.entity.ChatMessage;
import com.example.carrotmarket.modules.chat.domain.entity.ChatRoom;
import com.example.carrotmarket.modules.chat.repository.ChatMessageRepository;
import com.example.carrotmarket.modules.chat.repository.ChatRoomRepository;
import com.example.carrotmarket.modules.product.domain.entity.Product;
import com.example.carrotmarket.modules.product.repository.ProductRepository;
import com.example.carrotmarket.modules.user.domain.entity.User;
import com.example.carrotmarket.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public ChatRoomDto makeRoom(Long userIdx, Long productIdx){

        User user = userRepository.findById(userIdx).orElseThrow(() -> new CustomApiException(ResponseEnum.USER_NOT_FOUND));

        Optional<ChatRoom> chatRoomCkOpt = chatRoomRepository.findByProduct_IdxAndSender_Idx(productIdx,user.getIdx());
        if(chatRoomCkOpt.isPresent()){
            // 현재 방이 존재하면 throw!
            throw new CustomApiException(ResponseEnum.CHAT_ROOM_MAKE_EXIST);
        }

        // 존재하지 않는 상품이면 throw
        Product product =  productRepository.findByIdx(productIdx).orElseThrow(() -> new CustomApiException(ResponseEnum.CHAT_ROOM_MAKE_PRODUCT_NOT_EXIST));

        if(product.getUser().getIdx().equals(userIdx)){
            // 자기 자신에게는 보내지 못하게!
            throw new CustomApiException(ResponseEnum.CHAT_ROOM_CAN_NOT_TO_ME);
        }

        ChatRoom chatRoom = ChatRoom.builder()
                        .product(product)
                        .sender(user)
                        .build();

        chatRoomRepository.save(chatRoom);
        return new ChatRoomDto(chatRoom);

    }

    @Transactional
    public List<ChatRoomDto> roomList(User user){
        List<ChatRoom> roomOpt = chatRoomRepository.findAllByUserIdxFetchAll(user.getIdx());
        return roomOpt.stream().map(ChatRoomDto::new).collect(Collectors.toList());
    }

    @Transactional
    public ChatRoomDto roomDetail(User user, Long roomIdx){
        ChatRoom chatRoom = chatRoomRepository.findByIdxFetchMessages(roomIdx).orElseThrow(() -> new CustomApiException(ResponseEnum.CHAT_ROOM_NOT_EXIST));
        checkRoomPermission(chatRoom, user);
        return new ChatRoomDto(chatRoom);
    }

    private void checkRoomPermission(ChatRoom chatRoom, User user){
        Long productUserIdx = chatRoom.getProduct().getUser().getIdx();
        Long senderIdx = chatRoom.getSender().getIdx();
        if(!(productUserIdx.equals(user.getIdx()) || senderIdx.equals(user.getIdx()))){
            // 내가 접근할 수 있는 채팅방이 아니면 throw!
            throw new CustomApiException(ResponseEnum.CHAT_ROOM_NOT_PERMISSION);
        }
    }

    @Transactional
    public ChatRoomDto sendMessage(ChatRequestDto message, User user) {

        ChatRoom chatRoom = chatRoomRepository.findByIdx(message.getRoomIdx()).orElseThrow(() -> new CustomApiException(ResponseEnum.CHAT_ROOM_NOT_EXIST));

        checkRoomPermission(chatRoom, user);

        ChatMessageType chatMessageType = chatRoom.getProduct().getUser().getIdx().equals(user.getIdx()) ?
                ChatMessageType.PRODUCT_OWNER_TO_SENDER:
                ChatMessageType.SENDER_TO_PRODUCT_OWNER;

        ChatMessage chatMessage = ChatMessage.builder()
                .messageType(chatMessageType)
                .chatRoom(chatRoom)
                .content(message.getContent())
                .build();

        chatMessageRepository.save(chatMessage);

        return new ChatRoomDto(chatRoom, chatMessage);
    }

}
