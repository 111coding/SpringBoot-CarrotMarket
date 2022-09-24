package com.example.carrotmarket.modules.address.repository;

import com.example.carrotmarket.modules.address.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByFullName(String fullName);
}
