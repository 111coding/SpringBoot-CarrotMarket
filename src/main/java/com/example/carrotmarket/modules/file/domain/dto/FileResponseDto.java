package com.example.carrotmarket.modules.file.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;


@Data
@NoArgsConstructor
public class FileResponseDto {
    private Resource resource;
    private HttpHeaders headers;
}