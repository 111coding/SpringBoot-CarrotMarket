package com.example.carrotmarket.modules.user.service;

import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.handler.exception.CustomApiException;
import com.example.carrotmarket.modules.user.domain.entity.User;
import com.example.carrotmarket.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    final private UserRepository userRepository;


    public void nicknameCk(String nickname){
        Optional<User> userOpt = userRepository.findByNickname(nickname);
        if(userOpt.isPresent()){
            throw new CustomApiException(ResponseEnum.USER_USERNAME_CK_FAIL);
        }
    }

}
