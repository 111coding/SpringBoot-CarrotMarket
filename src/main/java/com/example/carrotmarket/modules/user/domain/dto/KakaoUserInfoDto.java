package com.example.carrotmarket.modules.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
public class KakaoUserInfoDto {
    private String userId;
    private String nickname;

    public KakaoUserInfoDto(Map<String, Object> map){
        this.userId = map.get("id").toString();
        this.nickname = ((Map) map.get("properties")).get("nickname").toString();
    }
}