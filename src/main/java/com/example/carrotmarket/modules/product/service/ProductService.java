package com.example.carrotmarket.modules.product.service;

import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.handler.exception.CustomApiException;
import com.example.carrotmarket.modules.product.domain.dto.ProductListResponseDto;
import com.example.carrotmarket.modules.product.domain.dto.ProductRequestDto;
import com.example.carrotmarket.modules.product.domain.entity.Product;
import com.example.carrotmarket.modules.product.domain.entity.ProductLike;
import com.example.carrotmarket.modules.product.repository.ProductLikeRepository;
import com.example.carrotmarket.modules.product.repository.ProductRepository;
import com.example.carrotmarket.modules.user.domain.entity.User;
import com.example.carrotmarket.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductLikeRepository productLikeRepository;


    public void save(ProductRequestDto dto, User principalUser){

        Optional<User> userOpt = userRepository.findByUserIdxFetchAddresses(principalUser.getIdx());

        if(userOpt.isEmpty()){
            throw new CustomApiException(ResponseEnum.USER_NOT_FOUND);
        }

        User user = userOpt.get();

        // 회원이 보유하고 있는 동네인지 체크
         boolean isAddressPresent = user.getAddresses()
                 .stream().anyMatch(userAddress -> userAddress.getAddress().getIdx().equals(dto.getAddressIdx()));

         if(!isAddressPresent){
             throw new CustomApiException(ResponseEnum.PRODUCT_UPLOAD_ADDRESS_NOT_MATCHED);
         }

        Product product = new Product(dto, user);
        product.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        product.setUpdateRemainCnt(2);

        productRepository.save(product);
    }

    public boolean like(Long userIdx, Long productIdx){
        // 현재 좋아요 상태 리턴
        try{
            Optional<ProductLike> like = productLikeRepository.findByUser_IdxAndProduct_Idx(userIdx, productIdx);
            if(like.isPresent()){
                productLikeRepository.delete(like.get());
                return false;
            }else{
                Product product = Product.builder().idx(productIdx).build();
                User user = User.builder().idx(userIdx).build();
                ProductLike productLike = ProductLike.builder()
                        .product(product)
                        .user(user)
                        .build();
                productLikeRepository.save(productLike);
                return true;
            }
        }catch (Exception e){
            throw new CustomApiException(ResponseEnum.PRODUCT_LIKE_FAIL);
        }
    }

    @Transactional
    public Page<ProductListResponseDto> list(Long addressIdx, Pageable pageable){
        try{
            return productRepository.list(addressIdx,pageable).map(ProductListResponseDto::new);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomApiException(ResponseEnum.PRODUCT_LIST_FAIL);
        }
    }

}
