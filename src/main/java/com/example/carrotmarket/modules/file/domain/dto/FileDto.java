package com.example.carrotmarket.modules.file.domain.dto;

import com.example.carrotmarket.modules.file.domain.entity.File;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class FileDto {
    private long idx;
    private String url;
    private String originName;
    private String contentType;
    private Timestamp createAt;

    public FileDto(File file){
        this.idx = file.getIdx();
        this.url = "http://localhost:8080/api/file/" + file.getUuid();
        this.originName = file.getOriginName();
        this.contentType = file.getContentType();
        this.createAt = file.getCreateAt();
    }
}