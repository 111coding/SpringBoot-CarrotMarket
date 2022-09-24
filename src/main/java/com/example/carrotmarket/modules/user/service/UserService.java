package com.example.carrotmarket.modules.user.service;

import com.example.carrotmarket.config.jwt.KakaoProvider;
import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.enums.UserRoleType;
import com.example.carrotmarket.handler.exception.CustomApiException;
import com.example.carrotmarket.modules.address.domain.entity.Address;
import com.example.carrotmarket.modules.address.service.AddressService;
import com.example.carrotmarket.modules.user.domain.dto.JoinRequestDto;
import com.example.carrotmarket.modules.user.domain.dto.KakaoUserInfoDto;
import com.example.carrotmarket.modules.user.domain.dto.UserDto;
import com.example.carrotmarket.modules.user.domain.entity.User;
import com.example.carrotmarket.modules.user.domain.entity.UserAddress;
import com.example.carrotmarket.modules.user.repository.UserAddressRepository;
import com.example.carrotmarket.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    final private UserRepository userRepository;

    final private KakaoProvider kakaoProvider;

    final private AddressService addressService;

    final private BCryptPasswordEncoder bCryptPasswordEncoder;

    final private UserAddressRepository userAddressRepository;


    public void nicknameCk(String nickname){
        Optional<User> userOpt = userRepository.findByNickname(nickname);
        if(userOpt.isPresent()){
            throw new CustomApiException(ResponseEnum.USER_USERNAME_CK_FAIL);
        }
    }

    @Transactional
    public void join(JoinRequestDto dto){
        KakaoUserInfoDto kakaoUserInfoDto = kakaoProvider.login(dto.getToken());
        try {
            // TODO customException
            String uid = kakaoUserInfoDto.getUserId();

            User user = User
                    .builder()
                    .username(uid)
                    .password(bCryptPasswordEncoder.encode(uid)) // default 암호화 때문에 kakao userId 암호화 해서 사용
                    .nickname(dto.getNickname())
                    .roles(UserRoleType.USER.getPrefixValue())
                    .build();

            userRepository.save(user);

            Address address = addressService.findOrInsertAddress(dto.getAddressFullName());
            UserAddress userAddress = UserAddress.builder()
                    .user(user)
                    .address(address)
                    .defaultYn(true)
                    .build();
            userAddressRepository.save(userAddress);
        }catch (Exception e){
            throw new CustomApiException(ResponseEnum.USER_JOIN_FAIL);
        }
    }

    public UserDto myInfo(Long userIdx){
        User user = userRepository.findByIdxFetchProfileImage(userIdx).orElseThrow(() -> new CustomApiException(ResponseEnum.USER_NOT_FOUND));
        return new UserDto(user);
    }


}
