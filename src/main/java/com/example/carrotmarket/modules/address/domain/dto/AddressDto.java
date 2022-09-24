package com.example.carrotmarket.modules.address.domain.dto;

import com.example.carrotmarket.modules.address.domain.entity.Address;
import lombok.Data;

@Data
public class AddressDto {
    private Long idx;
    private String fullName;
    private String displayName;

    public AddressDto(Address address){
        this.idx = address.getIdx();
        this.fullName = address.getFullName();
        this.displayName = address.getDisplayName();
    }
}