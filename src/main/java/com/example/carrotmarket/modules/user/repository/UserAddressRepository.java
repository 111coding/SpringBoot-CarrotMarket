package com.example.carrotmarket.modules.user.repository;

import com.example.carrotmarket.modules.user.domain.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findByUser_Idx(Long userIdx);
}
