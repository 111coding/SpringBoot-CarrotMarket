package com.example.carrotmarket.modules.product.repository;

import com.example.carrotmarket.modules.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"address"})
    // @EntityGraph(attributePaths = {"imageFiles", "user", "address","category"})
    @Query(   " FROM Product p"
            + " WHERE p.address.idx = :addressIdx"
    )
    Page<Product> list(@Param("addressIdx") Long addressIdx,
                       Pageable pageable);

//    // fetch join
//    @EntityGraph(attributePaths = {"imageFiles", "user", "address","category"})
//    @Query("SELECT DISTINCT p FROM Product p WHERE p.address.idx = :addressIdx AND (title LIKE %:searchStr% OR content LIKE %:searchStr%)")
//    List<Product> findTest(@Param("addressIdx") Long addressIdx,@Param("searchStr") String searchStr);
//    // Jpa Naming 으로 조회하려면 아래와 같이 메소드 명이 너무 길어짐
//    @EntityGraph(attributePaths = {"imageFiles", "user", "address","category"})
//    List<Product> findByAddressAndTitleContainsOrAddressAndContentContains(Address address, String title,Address address2, String content);
}
