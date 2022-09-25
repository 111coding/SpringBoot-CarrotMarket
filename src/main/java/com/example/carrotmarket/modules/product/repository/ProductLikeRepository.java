package com.example.carrotmarket.modules.product.repository;

import com.example.carrotmarket.modules.product.domain.entity.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {
    Optional<ProductLike> findByUser_IdxAndProduct_Idx(Long userIdx, Long productIdx);
}
