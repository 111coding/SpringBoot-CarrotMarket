package com.example.carrotmarket.modules.user.repository;

import com.example.carrotmarket.modules.user.domain.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);

    @EntityGraph(attributePaths = {"profileImage"})
    @Query(" FROM User user WHERE user.idx = :userIdx")
    Optional<User> findByIdxFetchProfileImage(@Param("userIdx") Long userIdx);
}
