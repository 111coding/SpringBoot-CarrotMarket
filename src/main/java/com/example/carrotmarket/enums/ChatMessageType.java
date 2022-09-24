package com.example.carrotmarket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatMessageType {
    SENDER_TO_PRODUCT_OWNER,
    PRODUCT_OWNER_TO_SENDER;
}