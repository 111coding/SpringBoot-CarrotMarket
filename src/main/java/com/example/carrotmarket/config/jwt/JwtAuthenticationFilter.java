package com.example.carrotmarket.config.jwt;

import com.example.carrotmarket.common.ResponseDto;
import com.example.carrotmarket.config.auth.PrincipalDetails;
import com.example.carrotmarket.enums.LoginType;
import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.handler.exception.CustomAuthenticationException;
import com.example.carrotmarket.modules.user.domain.dto.KakaoUserInfoDto;
import com.example.carrotmarket.modules.user.domain.dto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 로그인 인증과정
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private final AuthenticationManager authenticationManager;
    private final KakaoProvider kakaoProvider;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;

    // Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
    // 인증 요청시에 실행되는 함수 => /login
    // refresh, login
    // 닉네임은 카카오 디폴트 닉네임 설정
    // kakao devaloper 에서 닉네임 필수 설정하기!
    // https://developers.kakao.com/console
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter : 진입");

        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper();
        LoginRequestDto loginRequestDto = null;
        try {
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (Exception e) {
            throw new CustomAuthenticationException(ResponseEnum.AUTH_BAD_REQUEST);
        }

        System.out.println("JwtAuthenticationFilter : "+loginRequestDto);

        String userId = null;

        if(loginRequestDto.getLoginType().equals(LoginType.LOGIN)){
            // 로그인
            KakaoUserInfoDto kakaoUserInfoDto = kakaoProvider.login(loginRequestDto.getToken());
            if(kakaoUserInfoDto == null){
                throw new CustomAuthenticationException(ResponseEnum.AUTH_INVALID_TOKEN);
            }
            userId = kakaoUserInfoDto.getUserId();
        }else{
            // refresh
            String refreshToken = redisTemplate.opsForValue().getAndDelete(loginRequestDto.getToken());
            if(refreshToken == null){
                throw new CustomAuthenticationException(ResponseEnum.AUTH_REFRESH_DOES_NOT_EXIST);
            }
            userId = jwtProvider.getUsername(refreshToken);
        }



        // 유저네임패스워드 토큰 생성
        // 어차피 userId를 직접 유저가 던져서 로그인 할 일 없으므로 pw도 userId로 설정
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        userId);

        System.out.println("JwtAuthenticationFilter : 토큰생성완료");

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomAuthenticationException(ResponseEnum.AUTH_NOT_JOINED);
        }

        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("Authentication : "+principalDetailis.getUser().getUsername());
        return authentication;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        CustomAuthenticationException exception = ((CustomAuthenticationException) failed);
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writer().writeValueAsString(new ResponseDto<>(exception.getResponseEnum()));
        response.getWriter().println(responseBody);
        response.setStatus(exception.getResponseEnum().getCode());
        response.setContentType("application/json");
    }

    // JWT Token 생성해서 response에 담아주기
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        System.out.println("successfulAuthentication");

        PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();
        String accessToken = jwtProvider.createAccessToken(principalDetailis.getUser().getIdx(),principalDetailis.getUser().getUsername());
        String refreshToken = jwtProvider.createRefreshToken(principalDetailis.getUser().getIdx(),principalDetailis.getUser().getUsername());
        redisTemplate.opsForValue().set(accessToken, refreshToken);

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+accessToken);
    }

}