package com.example.carrotmarket.handler.exception;

import com.example.carrotmarket.enums.ResponseEnum;

public class CustomApiException extends RuntimeException{

    private final ResponseEnum responseEnum;

    public CustomApiException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }
}