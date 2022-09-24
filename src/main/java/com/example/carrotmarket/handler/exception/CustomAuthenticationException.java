package com.example.carrotmarket.handler.exception;

import com.example.carrotmarket.enums.ResponseEnum;
import org.springframework.security.authentication.AuthenticationServiceException;

public class CustomAuthenticationException extends AuthenticationServiceException {

    private final ResponseEnum responseEnum;

    public CustomAuthenticationException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }
}