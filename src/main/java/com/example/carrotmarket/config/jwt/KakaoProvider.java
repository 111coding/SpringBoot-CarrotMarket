package com.example.carrotmarket.config.jwt;

import com.example.carrotmarket.modules.user.domain.dto.KakaoUserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

@Component
public class KakaoProvider {


    // case 1 (admin sdk 필요, app id 몰라도됨)
    // https://kapi.kakao.com/v2/user/me (사용자 access token 포함해서 날림. admin key 필요X, 유저 디테일 정보 들어가있음.) 으로 응답받은 userid로
    // https://kapi.kakao.com/v2/user/me?target_id_type=user_id&target_id= 에 admin key 싣어서 날린 후 유저정보 가져옴(이 유저가 내 앱에 등록된 사람인지 구별을 두번째 때 함)
    // case 2 (admin sdk 필요 X, but app id 알아야함)
    // https://kapi.kakao.com/v1/user/access_token_info (유저디테일정보 안들어가있음) 으로 응답 받은 user의 app_id를 비교 후
    // https://kapi.kakao.com/v2/user/me (user access token 날려서) 요청 => 유저 디테일 정보

    // case 1 채택

    private String requestURL = "https://kapi.kakao.com/v2/user/me";

    @Value("${key.oauth.kakao}")
    private String adminKey;

    public KakaoUserInfoDto login(String accessToken){
        KakaoUserInfoDto preDto = preAuth(accessToken);
        if(preDto != null){
            return finalAuth(preDto.getUserId());
        }
        return null;
    }

    private KakaoUserInfoDto preAuth(String accessToken) {
        try{
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            putCommonHeader(conn);

            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            if(responseCode == 200){
                Map map = new ObjectMapper().readValue(conn.getInputStream(), Map.class);
                return new KakaoUserInfoDto(map);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private KakaoUserInfoDto finalAuth(String uid) {

        try{
            URL url = new URL(requestURL + "?target_id_type=user_id&target_id=" + uid);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            putCommonHeader(conn);
            conn.setRequestProperty("Authorization", "KakaoAK " + adminKey);

            int responseCode = conn.getResponseCode();
            if(responseCode == 200){
                Map map = new ObjectMapper().readValue(conn.getInputStream(), Map.class);
                return new KakaoUserInfoDto(map);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void putCommonHeader(HttpURLConnection conn) throws ProtocolException {
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Host","kapi.kakao.com");
        conn.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=utf-8");
    }
}
