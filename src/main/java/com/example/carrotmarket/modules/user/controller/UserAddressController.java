package com.example.carrotmarket.modules.user.controller;

import com.example.carrotmarket.common.ResponseDto;
import com.example.carrotmarket.config.auth.PrincipalDetails;
import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.modules.user.domain.dto.UserAddressDto;
import com.example.carrotmarket.modules.user.domain.dto.UserAddressRequestDto;
import com.example.carrotmarket.modules.user.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/address")
public class UserAddressController {

    private final UserAddressService userAddressService;

    @PostMapping
    public ResponseEntity<?> addressAdd(@Valid @RequestBody UserAddressRequestDto addressRequestDto,
                                  BindingResult bindingResult,
                                  Authentication authentication){
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        UserAddressDto result = userAddressService.addressAdd(addressRequestDto,details.getUser());
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_ADDRESS_ADD_SUCCESS, result),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> myAddresses(Authentication authentication){
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        List<UserAddressDto> result = userAddressService.myAddresses(details.getUser().getIdx());
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_MY_ADDRESSES_SUCCESS, result),HttpStatus.OK);
    }

    @PostMapping("/defaultYn/{addressIdx}")
    public ResponseEntity<?> changeDefaultYn(@PathVariable Long addressIdx,Authentication authentication){
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        userAddressService.changeDefaultYn(addressIdx,details.getUser().getIdx());
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_ADDRESS_UPDATE_SUCCESS), HttpStatus.OK);
    }

    @DeleteMapping("/{addressIdx}")
    public ResponseEntity<?> addressRemove(@PathVariable Long addressIdx,Authentication authentication){
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        userAddressService.addressRemove(addressIdx,details.getUser());
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_ADDRESS_REMOVE_SUCCESS), HttpStatus.OK);
    }

}
