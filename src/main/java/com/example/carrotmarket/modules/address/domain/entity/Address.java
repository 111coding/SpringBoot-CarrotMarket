package com.example.carrotmarket.modules.address.domain.entity;

import com.example.carrotmarket.modules.product.domain.entity.Product;
import com.example.carrotmarket.modules.user.domain.entity.UserAddress;
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
// 유저, 상품, 동네생활 사용
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, unique = true)
    private String fullName;

    // 유저한테는 full name만 받고 split해서 처리할거!
    @Column(nullable = false)
    private String displayName;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;


    @OneToMany(mappedBy = "address")
    private List<UserAddress> userAddresses;


    @OneToMany(mappedBy = "address")
    private List<Product> products;

    // ManyToMany mappedBy는 참조키 테이블 인서트할 주체가 아닌 곳에!(유저인서트할때 address 넣을 거라서)
}