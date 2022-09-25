package com.example.carrotmarket.modules.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListRequestDto {
    private String nickname = "";
    private Long addressIdx = 0L;
    private String category = "";
    private String searchStr = "";
    private int minPrice = -1;
    private int maxPrice = 999999999; // 당근마켓도 글 등록할 때 max 999,999,999으로 잡음! 이 이상으로 거래하는건 상식적으로 불가능
}