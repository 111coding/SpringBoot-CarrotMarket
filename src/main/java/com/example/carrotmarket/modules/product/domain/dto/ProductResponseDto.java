package com.example.carrotmarket.modules.product.domain.dto;

import com.example.carrotmarket.modules.address.domain.dto.AddressDto;
import com.example.carrotmarket.modules.file.domain.dto.FileDto;
import com.example.carrotmarket.modules.product.domain.entity.Product;
import com.example.carrotmarket.modules.user.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Long idx;
    private String title;
    private String content;
    private List<FileDto> imageFiles;
    private UserDto user;
    private AddressDto address;
    private ProductCategoryDto category;

    private int price;

    private int likeCnt;
    private boolean myLike;

    private Timestamp updateAt;
    private Timestamp createAt;

    public ProductResponseDto(Product product){
        this.idx = product.getIdx();
        this.title = product.getTitle();
        this.content = product.getContent();
        this.imageFiles = product.getImageFiles().stream().map(FileDto::new).collect(Collectors.toList());
        this.user = new UserDto(product.getUser());
        this.address = new AddressDto(product.getAddress());
        this.category = new ProductCategoryDto(product.getCategory());
        this.price = product.getPrice();
        this.likeCnt = product.getLikes().size();
        this.myLike = false;
        this.updateAt = product.getUpdateAt();
        this.createAt = product.getCreateAt();
    }

}