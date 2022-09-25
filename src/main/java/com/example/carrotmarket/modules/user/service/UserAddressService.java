package com.example.carrotmarket.modules.user.service;

import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.handler.exception.CustomApiException;
import com.example.carrotmarket.modules.address.domain.entity.Address;
import com.example.carrotmarket.modules.address.service.AddressService;
import com.example.carrotmarket.modules.user.domain.dto.UserAddressDto;
import com.example.carrotmarket.modules.user.domain.dto.UserAddressRequestDto;
import com.example.carrotmarket.modules.user.domain.entity.User;
import com.example.carrotmarket.modules.user.domain.entity.UserAddress;
import com.example.carrotmarket.modules.user.repository.UserAddressRepository;
import com.example.carrotmarket.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAddressService {


    final private UserRepository userRepository;

    final private UserAddressRepository userAddressRepository;

    final private AddressService addressService;


    @Transactional
    public UserAddressDto addressAdd(UserAddressRequestDto dto, User principalUser){
        Optional<User> userOpt = userRepository.findByUserIdxFetchAddresses(principalUser.getIdx());
        if(userOpt.isEmpty()){
            throw new CustomApiException(ResponseEnum.USER_NOT_FOUND);
        }
        User user = userOpt.get();
        if(user.getAddresses().size() == 2){
            // 최대 2개의 주소만 허용
            throw new CustomApiException(ResponseEnum.USER_ADDRESS_ADD_OVER_MAX);
        }

        Address address = addressService.findOrInsertAddress(dto.getAddressFullName());

        // 중복일 시 return
        List<UserAddress> presentList = user.getAddresses().stream()
                .filter(userAddress -> Objects.equals(userAddress.getAddress().getIdx(), address.getIdx()))
                .collect(Collectors.toList());
        if(presentList.size() > 0){
            return null;
        }

        user.getAddresses().forEach(userAddress -> userAddress.setDefaultYn(false));

        UserAddress userAddress = UserAddress.builder()
                        .user(user)
                        .address(address)
                        .defaultYn(true)
                        .build();

        userAddressRepository.save(userAddress);
        return new UserAddressDto(userAddress);
    }

    public List<UserAddressDto> myAddresses(Long userIdx){
        return userAddressRepository.findByUser_Idx(userIdx)
                .stream().map(UserAddressDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void changeDefaultYn(Long addressIdx,Long userIdx){
        List<UserAddress> userAddresses = userAddressRepository.findByUserIdxFetch(userIdx);
        if(userAddresses.isEmpty()){
            throw new CustomApiException(ResponseEnum.USER_NOT_FOUND);
        }

        boolean isContain = userAddresses.stream().anyMatch(userAddress -> userAddress.getAddress().getIdx().equals(addressIdx));

        if(!isContain){
            throw new CustomApiException(ResponseEnum.USER_ADDRESS_NOT_FOUND);
        }

        for (UserAddress address:userAddresses) {
            boolean defaultYn = address.getAddress().getIdx().equals(addressIdx);
            address.setDefaultYn(defaultYn);
        }

    }

}
