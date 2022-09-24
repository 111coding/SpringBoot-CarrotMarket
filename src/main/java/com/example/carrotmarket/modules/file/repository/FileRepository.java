package com.example.carrotmarket.modules.file.repository;

import com.example.carrotmarket.modules.file.domain.dto.FileDto;
import com.example.carrotmarket.modules.file.domain.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByUuid(String uuid);
}
