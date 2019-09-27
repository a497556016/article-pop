package com.heshaowei.article_popularize.metadata.article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/upload")
    public ResponseEntity<String> upload(MultipartHttpServletRequest request){
        request.getFileMap().forEach((name, file) -> {
            System.out.println(file.getOriginalFilename());
        });

        return ResponseEntity.ok("save path");
    }
}
