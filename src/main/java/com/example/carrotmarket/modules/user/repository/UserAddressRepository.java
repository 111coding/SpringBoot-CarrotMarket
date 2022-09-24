package com.example.carrotmarket.modules.user.repository;

import com.example.carrotmarket.modules.user.domain.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
