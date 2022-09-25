package com.example.carrotmarket.modules.user.repository;

import com.example.carrotmarket.modules.user.domain.entity.UserAddress;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findByUser_Idx(Long userIdx);

    @EntityGraph(attributePaths = {"user","address"})
    @Query("SELECT DISTINCT a FROM UserAddress a" +
            " WHERE a.user.idx = :userIdx")
    List<UserAddress> findByUserIdxFetch(@Param("userIdx") Long userIdx);
}
