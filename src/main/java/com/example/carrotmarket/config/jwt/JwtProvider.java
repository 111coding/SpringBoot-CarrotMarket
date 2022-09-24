package com.example.carrotmarket.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.handler.exception.CustomAuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    public String getUsername(String jwt) {
        try {
            return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwt)
                    .getClaim("username").asString();
        }catch (Exception e){
            throw new CustomAuthenticationException(ResponseEnum.AUTH_REFRESH_EXPIRED);
        }
    }

    public String createAccessToken(Long userIdx, String username){
        return createToken(userIdx,username,JwtProperties.EXPIRATION_TIME);
    }

    public String createRefreshToken(Long userIdx, String username){
        return createToken(userIdx,username,JwtProperties.EXPIRATION_TIME_REFRESH);
    }

    private String createToken(Long userIdx, String username, Long expirationTime){
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis()+expirationTime))
                .withClaim("id", userIdx)
                .withClaim("username", username)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }
}
