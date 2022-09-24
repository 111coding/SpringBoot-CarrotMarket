package com.example.carrotmarket.modules.file.domain.entity;

import com.example.carrotmarket.modules.product.domain.entity.Product;
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
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 저장되는 파일네임
    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private String originName;

    // 유저한테는 full name만 받고 split해서 처리할거!
    @Column(nullable = false)
    private String contentType;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;

    // ManyToMany mappedBy는 참조키 테이블 인서트할 주체가 아닌 곳에!
    @ManyToMany(mappedBy = "imageFiles")
    private List<Product> products;

}