package com.example.carrotmarket.modules.product.repository;

import com.example.carrotmarket.modules.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
