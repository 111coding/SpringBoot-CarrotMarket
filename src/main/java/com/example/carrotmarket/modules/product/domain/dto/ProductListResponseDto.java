package com.example.carrotmarket.modules.product.domain.dto;

import com.example.carrotmarket.modules.address.domain.dto.AddressDto;
import com.example.carrotmarket.modules.file.domain.dto.FileDto;
import com.example.carrotmarket.modules.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListResponseDto {

    private Long idx;
    private String title;
    private FileDto thumbnail;
    private AddressDto address;
    private int price;
    private int likeCnt;
    private Timestamp updateAt;
    private Timestamp createAt;

    public ProductListResponseDto(Product product){
        this.idx = product.getIdx();
        this.title = product.getTitle();
        if(!product.getImageFiles().isEmpty()){
            this.thumbnail = new FileDto(product.getImageFiles().get(0));
        }
        this.address = new AddressDto(product.getAddress());
        this.price = product.getPrice();
        this.likeCnt = product.getLikes().size();
        this.updateAt = product.getUpdateAt();
        this.createAt = product.getCreateAt();
    }

}