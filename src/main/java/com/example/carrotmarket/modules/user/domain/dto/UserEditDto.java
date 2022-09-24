package com.example.carrotmarket.modules.user.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class UserEditDto {
    @Size(min = 2, max = 15)
    @NotBlank
    private String nickname;
    @NotNull
    private Long profileImageIdx;
}