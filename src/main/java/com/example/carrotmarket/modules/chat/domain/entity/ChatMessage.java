package com.example.carrotmarket.modules.chat.domain.entity;

import com.example.carrotmarket.enums.ChatMessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChatMessageType messageType;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_idx", nullable = false)
    private ChatRoom chatRoom;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;
}
