package com.example.carrotmarket.modules.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    @NotNull
    private Long idx;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private List<Long> imageFileIdxList;
    @NotNull
    private Long addressIdx;
    @NotNull
    private Long categoryIdx;
    @NotNull
    private int price;
}