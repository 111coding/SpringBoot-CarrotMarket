package com.example.carrotmarket.config.auth;

import com.example.carrotmarket.modules.user.domain.entity.User;
import com.example.carrotmarket.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("PrincipalDetailsService : 진입");

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return new PrincipalDetails(user.get());
        }
        return null;
    }
}