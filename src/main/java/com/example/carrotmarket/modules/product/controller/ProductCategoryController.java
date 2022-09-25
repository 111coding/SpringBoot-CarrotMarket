package com.example.carrotmarket.modules.product.controller;

import com.example.carrotmarket.common.ResponseDto;
import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.modules.product.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product/category")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping
    public ResponseEntity<?> list(){
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, productCategoryService.list()), HttpStatus.OK);
    }

}
