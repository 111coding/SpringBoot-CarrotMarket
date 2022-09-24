package com.example.carrotmarket.modules.address.service;

import com.example.carrotmarket.modules.address.domain.entity.Address;
import com.example.carrotmarket.modules.address.repository.AddressRepository;
import com.example.carrotmarket.utils.AddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Transactional
    public Address findOrInsertAddress(String addressFullName){
        Optional<Address> addressOpt = addressRepository.findByFullName(addressFullName);
        if(addressOpt.isPresent()){
            return addressOpt.get();
        }else{
            Address address = Address.builder()
                    .fullName(addressFullName)
                    .displayName(AddressUtils.getDisplayName(addressFullName))
                    .build();
            addressRepository.save(address);
            return address;
        }
    }
}
