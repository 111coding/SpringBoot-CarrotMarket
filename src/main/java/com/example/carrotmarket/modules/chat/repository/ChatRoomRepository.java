package com.example.carrotmarket.modules.chat.repository;

import com.example.carrotmarket.modules.chat.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {


    // [MAKE ROOM 하기 전 조회용]
    @EntityGraph(attributePaths = {"product.user", "sender"})
    Optional<ChatRoom> findByProduct_IdxAndSender_Idx(Long productIdx, Long senderIdx);


    // [Room List 가져오기!] 마지막 메시지 가져와야 돼서 ChatMessage 조인 필수!
    // 조인되는 테이블의 마지막 데이터를 가져와야하기 때문에 setMaxResult 안됨!
    // "product.imageFiles" => fetch multiple bags 때문에 select 한번 더
    @EntityGraph(attributePaths = {"product.user", "sender", "messages"})
    @Query( "SELECT DISTINCT room"
            + "  FROM ChatRoom room"
            + "  WHERE"
            + "    room.product.user.idx = :userIdx"
            + "  OR"
            + "    room.sender.idx = :userIdx")
    List<ChatRoom> findAllByUserIdxFetchAll(@Param("userIdx") Long userIdx);


    // [Room Detail 가져오기용!] ChatMessage 조인 필수!
    @EntityGraph(attributePaths = {"product.user", "sender", "messages"})
    @Query("FROM ChatRoom room WHERE room.idx = :roomIdx")
    Optional<ChatRoom> findByIdxFetchMessages(@Param("roomIdx") Long roomIdx);


    // [ChatMessage Save 전 조회용!]
    // List(messages)는 조인 불필요, DTO로 받으면 안됨!(ChatMessage save 시 사용)
    @EntityGraph(attributePaths = {"product.user", "sender"})
    Optional<ChatRoom> findByIdx(Long roomIdx);

}
