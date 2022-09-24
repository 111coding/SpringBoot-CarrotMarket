package com.example.carrotmarket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
    TEST_SUCCESS(200,"테스트 유효성 검사 성공"),
    TEST_FAIL(500, "테스트 유효성 검사 실패"),

    FILE_UPLOAD_SUCCESS(200, "파일 업로드에 성공하였습니다"),
    FILE_UPLOAD_FAIL(500, "파일 업로드에 실패하였습니다"),

    FILE_NOT_FOUND(404, "파일을 찾을 수 없습니다"),

    AUTH_BAD_REQUEST(403, "bad request"),
    AUTH_INVALID_TOKEN(401, "invalid token"),
    AUTH_NOT_JOINED(405, "not joined user"),
    AUTH_REFRESH_DOES_NOT_EXIST(401, "REFRESH_DOES_NOT_EXIST"),
    AUTH_REFRESH_EXPIRED(401, "AUTH_REFRESH_EXPIRED");


    private final int code;
    private final String message;

}