package com.example.carrotmarket.modules.user.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class JoinRequestDto {

    @Size(min = 2, max = 15)
    @NotBlank
    private String nickname;

    @NotBlank
    private String token;

    @NotBlank
    private String addressFullName;
}