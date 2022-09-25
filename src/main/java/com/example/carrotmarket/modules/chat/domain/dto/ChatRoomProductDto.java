package com.example.carrotmarket.modules.chat.domain.dto;

import com.example.carrotmarket.modules.address.domain.dto.AddressDto;
import com.example.carrotmarket.modules.product.domain.entity.Product;
import com.example.carrotmarket.modules.user.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomProductDto {

    private Long idx;
    private String title;
    private UserDto user;
    private AddressDto address;

    private int price;

    public ChatRoomProductDto(Product product){
        this.idx = product.getIdx();
        this.title = product.getTitle();
        this.user = new UserDto(product.getUser());
        this.address = new AddressDto(product.getAddress());
        this.price = product.getPrice();
    }


}
