package com.example.carrotmarket.modules.product.repository;

import com.example.carrotmarket.modules.product.domain.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
