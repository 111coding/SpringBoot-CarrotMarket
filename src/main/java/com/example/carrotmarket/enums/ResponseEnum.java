package com.example.carrotmarket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
    TEST_SUCCESS(200,"테스트 유효성 검사 성공"),
    TEST_FAIL(500, "테스트 유효성 검사 실패");


    private final int code;
    private final String message;

}