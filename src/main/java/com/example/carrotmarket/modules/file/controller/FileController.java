package com.example.carrotmarket.modules.file.controller;

import com.example.carrotmarket.common.ResponseDto;
import com.example.carrotmarket.enums.ResponseEnum;
import com.example.carrotmarket.modules.file.domain.dto.FileDto;
import com.example.carrotmarket.modules.file.domain.dto.FileResponseDto;
import com.example.carrotmarket.modules.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file){
        FileDto fileDto = fileService.upload(file);
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.FILE_UPLOAD_SUCCESS,fileDto),HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Resource> download(@PathVariable String uuid) {
        FileResponseDto responseDto = fileService.download(uuid);
        return new ResponseEntity<>(responseDto.getResource(), responseDto.getHeaders(), HttpStatus.OK);
    }
}
