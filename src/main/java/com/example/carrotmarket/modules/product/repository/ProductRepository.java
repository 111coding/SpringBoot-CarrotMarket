package com.example.carrotmarket.modules.product.repository;

import com.example.carrotmarket.modules.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


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



    // multiplebagfetchexception eager 에러 : OneToMany, ManyToMany인 Bag 두 개 이상을 EAGER로 fetch할 때 발생
    // 자바 컬렉션 프레임워크에서는 Bag이 없기 때문에 하이버네이트에서는 List를 Bag으로써 사용
    // Bag(Multiset)은 Set과 같이 순서가 없고, List와 같이 중복을 허용하는 자료구조 (https://en.wikipedia.org/wiki/Set_(abstract_data_type)#Multiset, https://stackoverflow.com/questions/13812283/difference-between-set-and-bag-in-hibernate)
    // 해결은 likes를 lazy로 불러와서 쿼리 두번 날리는거 vs likes를 Set으로 변경
    // likes는 순서가 상관 없으니 set으로 변경! -> list, search에서 에러! 다시 List로 원복
    // likes는 조회쿼리 하나 더 날리자!
    @EntityGraph(attributePaths = {"address", "category", "user.profileImage", "imageFiles"})
    Optional<Product> findByIdx(Long idx);
}
