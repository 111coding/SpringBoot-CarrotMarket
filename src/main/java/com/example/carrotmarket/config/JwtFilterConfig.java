package com.example.carrotmarket.config;

import com.example.carrotmarket.config.jwt.JwtAuthenticationFilter;
import com.example.carrotmarket.config.jwt.JwtAuthorizationFilter;
import com.example.carrotmarket.config.jwt.JwtProvider;
import com.example.carrotmarket.config.jwt.KakaoProvider;
import com.example.carrotmarket.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JwtFilterConfig extends AbstractHttpConfigurer<JwtFilterConfig, HttpSecurity> {

    private final UserRepository userRepository;
    private final KakaoProvider kakaoProvider;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http
                .addFilter(new JwtAuthenticationFilter(authenticationManager, kakaoProvider,jwtProvider,redisTemplate))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository,jwtProvider));
    }

}
