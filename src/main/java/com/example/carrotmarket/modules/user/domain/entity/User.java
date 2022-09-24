package com.example.carrotmarket.modules.user.domain.entity;

import com.example.carrotmarket.modules.file.domain.entity.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // kakao userId
    @Column(unique = true, nullable = false)
    private String username;

    // default 암호화 때문에 kakao userId 암호화 해서 사용
    @Column(unique = true, nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    private File profileImage;

    private String roles;

    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private Timestamp updateAt;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;

    @OneToMany(mappedBy = "user")
    @Column(nullable = false)
    private List<UserAddress> addresses;

    // ENUM으로 안하고 ,로 해서 구분해서 ROLE을 입력 -> 그걸 파싱!!
    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}