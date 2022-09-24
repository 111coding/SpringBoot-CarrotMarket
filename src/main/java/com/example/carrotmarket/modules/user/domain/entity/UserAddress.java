package com.example.carrotmarket.modules.user.domain.entity;

import com.example.carrotmarket.modules.address.domain.entity.Address;
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
@Table(
        uniqueConstraints={
                @UniqueConstraint(
                        name="user_address_unique",
                        columnNames={"user_idx", "address_idx"}
                )
        }
)
public class UserAddress {

    // ManyToMany 안쓴 이유!
    // defauilt YN같은 추가정보 넣으려고
    // Address는 Product와 함께 사용(Product는 defaultYn 없음)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name="user_idx", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="address_idx", nullable=false)
    private Address address;

    @Column(nullable = false)
    private boolean defaultYn;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;

}