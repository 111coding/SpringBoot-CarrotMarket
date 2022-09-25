package com.example.carrotmarket.modules.user.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserAddressRequestDto {
    @NotBlank
    private String addressFullName;
}