package com.example.carrotmarket.config;


import com.example.carrotmarket.enums.UserRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// WebSecurityConfigurerAdapter depreated 2.7.0
// SecurityFilterChain 권장
// https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
public class SecurityConfig {

    @Autowired
    private JwtFilterConfig jwtFilterConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(jwtFilterConfig)
                .and()
                .authorizeRequests()
                // "/api/user/test" permitAll 을 "/api/user/**" .hasRole("USER") 뒤에 하면 안먹힘!
                .antMatchers("/login").permitAll()
                .antMatchers("/api/user/join","/api/user/nicknameCk").permitAll()
                .antMatchers("/api/user/**").hasRole(UserRoleType.USER.getValue())
                .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // filter 자체를 안탐
        return (web) -> web
                .ignoring()
                .antMatchers("/api/file/**");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
