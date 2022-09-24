package com.example.carrotmarket.modules.product.domain.entity;

import com.example.carrotmarket.modules.address.domain.entity.Address;
import com.example.carrotmarket.modules.file.domain.entity.File;
import com.example.carrotmarket.modules.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int price;

    // 끌어올리기 가능 횟수
    @Column(nullable = false ,columnDefinition = "integer default 2")
    private int updateRemainCnt;

    @BatchSize(size=100)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "product_idx"),
            inverseJoinColumns = @JoinColumn(name = "file_idx")
    )
    private List<File> imageFiles;

    @ManyToOne
    private User user;

    @ManyToOne
    private Address address;

    @ManyToOne
    private ProductCategory category;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductLike> likes;

    // 끌어올리기용
    // @UpdateTimestamp => 이건 일반 글 수정할때에도 업데이트되니 끌어올리기 order 용으로 부적합! => Java code 로 삽입!
    // 타임존 맞춰줘야함!
    @Column(name = "update_at", nullable = false)
    private Timestamp updateAt;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;


}