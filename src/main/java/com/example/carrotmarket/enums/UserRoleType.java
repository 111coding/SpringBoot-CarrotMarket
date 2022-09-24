package com.example.carrotmarket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleType {

    USER(0,"USER","ROLE_USER");

    private final int code;
    private final String value;
    private final String prefixValue;
}