package com.example.carrotmarket.modules.chat.repository;

import com.example.carrotmarket.modules.chat.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {


    // [MAKE ROOM 하기 전 조회용]
    @EntityGraph(attributePaths = {"product.user", "sender"})
    Optional<ChatRoom> findByProduct_IdxAndSender_Idx(Long productIdx, Long senderIdx);

}
