package com.example.carrotmarket.modules.user.domain.dto;

import com.example.carrotmarket.modules.address.domain.entity.Address;
import com.example.carrotmarket.modules.user.domain.entity.UserAddress;
import lombok.Data;

@Data
public class UserAddressDto {
    private long idx;
    private String fullName;
    private String displayName;
    private boolean defaultYn;

    public UserAddressDto(UserAddress userAddress){
        Address address = userAddress.getAddress();
        this.idx = address.getIdx();
        this.fullName = address.getFullName();
        this.displayName = address.getDisplayName();
        this.defaultYn = userAddress.isDefaultYn();
    }
}