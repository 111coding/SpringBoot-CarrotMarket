package com.example.carrotmarket.modules.product.domain.dto;

import com.example.carrotmarket.modules.product.domain.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryDto {

    private Long idx;
    private String category;

    public ProductCategoryDto(ProductCategory category){
        this.idx = category.getIdx();
        this.category = category.getCategory();
    }

}