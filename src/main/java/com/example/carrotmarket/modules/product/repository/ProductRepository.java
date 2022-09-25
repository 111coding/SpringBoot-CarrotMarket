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


    // 회원별조회

    //    @EntityGraph(attributePaths = {"imageFiles", "user", "address","category"})
    @EntityGraph(attributePaths = {"address","user"})
    Page<Product> findByUser_Nickname(String userNickname, Pageable pageable);

    // 지역별 (카테고리, 가격범위, 정확도순-최신순, 검색어)
    // category LIKE로 한 이유 : 전체일 때 공백 넣을거임

    // Pageable 에 fetch join 을 사용하면 limit 문 사용 X
    // 1:N N:N 관계를 fetch join 하게 되면, 몇 개의 row까지 가지고 와야하는지 예측할 수 없어서 firstResult, maxResults 설정 값이 무시
    // limit 을 두지 않고 조회한 결과를 모두 java memory 에 올려놓고 pagination 을 위한 계산
    // 해결 : @BatchSize(size=100) & @ManyToMany(fetch = FetchType.EAGER)
    // firstResult/maxResults specified with collection fetch; applying in memory!
    //
    // 검색 정확도 순 하려면 MetadataBuilderContributor 사용해서 replace 함수 등록해줘서
    // 1 - LENGTH(function('REPLACE',c.title,:searchStr,'')) / LENGTH(c.title) 로 사용하고 interface(겟프로덕트, 겟정확도) 만들어서 Page<만든인터페이스> 사용
    // application.yml : MetadataBuilderContributor -> metadata_builder_contributor : com.example.carrotmarket.config.DatabaseFunctionConfig
    // @EntityGraph(attributePaths = {"imageFiles", "user", "address","category"})
    @EntityGraph(attributePaths = {"address", "category", "user"})
    @Query(   " FROM Product p"
            + " WHERE p.address.idx = :addressIdx"
            + " AND p.category.category LIKE %:category%"
            + " AND (title LIKE %:searchStr% OR content LIKE %:searchStr%)"
            + " AND p.price >= :minPrice"
            + " AND p.price <= :maxPrice"
    )
    Page<Product> search(@Param("addressIdx") Long addressIdx,
                         @Param("category") String category,
                         @Param("searchStr") String searchStr,
                         @Param("minPrice") int minPrice,
                         @Param("maxPrice") int maxPrice,
                         Pageable pageable);
}
