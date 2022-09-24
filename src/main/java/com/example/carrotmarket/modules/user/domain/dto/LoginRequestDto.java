package com.example.carrotmarket.modules.user.domain.dto;

import com.example.carrotmarket.enums.LoginType;
import lombok.Data;


@Data
public class LoginRequestDto {
    private LoginType loginType;
    private String token;
}