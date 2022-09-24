package com.example.carrotmarket.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // filter 자체를 안탐
        return (web) -> web
                .ignoring()
                .antMatchers("/api/file/**");
    }

}
