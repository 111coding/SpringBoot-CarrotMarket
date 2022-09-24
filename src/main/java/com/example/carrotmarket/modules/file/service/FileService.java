package com.example.carrotmarket.modules.file.service;

import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.handler.exception.CustomApiException;
import com.example.carrotmarket.modules.file.domain.dto.FileDto;
import com.example.carrotmarket.modules.file.domain.dto.FileResponseDto;
import com.example.carrotmarket.modules.file.domain.entity.File;
import com.example.carrotmarket.modules.file.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;


@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public FileDto upload(MultipartFile multipartFile){
        UUID uuid = UUID.randomUUID();
        Path filePath = Paths.get(uploadPath + "/" + uuid);
        System.out.println("filePath : "+filePath);
        try {
            Files.write(filePath, multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomApiException(ResponseEnum.FILE_UPLOAD_FAIL);
        }
        File file = File.builder()
                .uuid(uuid.toString())
                .originName(multipartFile.getOriginalFilename())
                .contentType(multipartFile.getContentType())
                .build();
        fileRepository.save(file);
        return new FileDto(file);
    }


    public FileResponseDto download(String uuid){
        Optional<File> fileOpt = fileRepository.findByUuid(uuid);
        if(fileOpt.isEmpty()){
            throw new CustomApiException(ResponseEnum.FILE_NOT_FOUND);
        }
        File file = fileOpt.get();

        Path path = Paths.get(uploadPath + "/" + uuid);

        FileResponseDto responseDto = new FileResponseDto();

        responseDto.setHeaders(new HttpHeaders());
        responseDto.getHeaders().add(HttpHeaders.CONTENT_TYPE, file.getContentType());

        try{
            responseDto.setResource(new InputStreamResource(Files.newInputStream(path)));
        }catch (Exception e){
            throw new CustomApiException(ResponseEnum.FILE_NOT_FOUND);
        }

        return responseDto;
    }


}
