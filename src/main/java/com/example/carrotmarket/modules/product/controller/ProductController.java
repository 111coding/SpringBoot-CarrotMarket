package com.example.carrotmarket.modules.product.controller;

import com.example.carrotmarket.common.ResponseDto;
import com.example.carrotmarket.config.auth.PrincipalDetails;
import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.modules.product.domain.dto.ProductListRequestDto;
import com.example.carrotmarket.modules.product.domain.dto.ProductRequestDto;
import com.example.carrotmarket.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    // 피요한 기능들
    // 지역별(insert 시 지역 넣음! 이유는 회원이 유저정보 변경해도 해당지역 물품으로 남게하려고,유저가 2가지 지역 가지고있으면 애매),
    // 지역은 항상 무조건 같이 붙어서 검색!
    // 카테고리별 리스트
    // 회원별

    @Autowired
    ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody ProductRequestDto productRequestDto,
                               BindingResult bindingResult,
                               Authentication authentication){
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        productService.save(productRequestDto,details.getUser());
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_UPLOAD_SUCCESS), HttpStatus.CREATED);
    }

    @PostMapping("/like/{productIdx}")
    public ResponseEntity<?> like(@PathVariable Long productIdx, Authentication authentication){
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_LIKE_SUCCESS, productService.like(details.getUser().getIdx(),productIdx)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam Long addressIdx,
            @PageableDefault(size=10, sort="idx", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_LIST_SUCCESS, productService.list(addressIdx,pageable)), HttpStatus.OK);
    }

    @GetMapping("/{productIdx}")
    public ResponseEntity<?> detail(@PathVariable Long productIdx, Authentication authentication){
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_DETAIL_SUCCESS, productService.detail(productIdx,details.getUser().getIdx())), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            ProductListRequestDto requestDto,
            @PageableDefault(size=10, sort="idx", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_SEARCH_SUCCESS, productService.search(requestDto,pageable)), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody ProductRequestDto productRequestDto,
                                    BindingResult bindingResult, Authentication authentication){
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        productService.update(productRequestDto,details.getUser().getIdx());
        return new ResponseEntity<>( new ResponseDto<>(ResponseEnum.PRODUCT_UPDATE_SUCCESS), HttpStatus.OK);
    }

    @PostMapping("/updateTime/{productIdx}")
    public ResponseEntity<?> updateTime(@PathVariable Long productIdx, Authentication authentication){
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        productService.updateUpdateAt(details.getUser().getIdx(),productIdx);
        return new ResponseEntity<>( new ResponseDto<>(ResponseEnum.PRODUCT_UPDATE_TIME_SUCCESS), HttpStatus.OK);
    }

}
