package com.example.carrotmarket.modules.chat.domain.entity;

import com.example.carrotmarket.modules.product.domain.entity.Product;
import com.example.carrotmarket.modules.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_idx", nullable = false)
    private User sender;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "chatRoom")
    private List<ChatMessage> messages;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;
}
