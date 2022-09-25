package com.example.carrotmarket.modules.product.service;

import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.handler.exception.CustomApiException;
import com.example.carrotmarket.modules.product.domain.dto.*;
import com.example.carrotmarket.modules.product.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Transactional
    public List<ProductCategoryDto> list(){
        try{
            return productCategoryRepository.findAll()
                    .stream()
                    .map(ProductCategoryDto::new)
                    .collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomApiException(ResponseEnum.PRODUCT_CATEGORY_FAIL);
        }
    }

}
