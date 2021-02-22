package com.example.spaceapi.controller;

import com.example.spaceapi.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImageTesting(@PathVariable Long id) {
        return imageService.getImage(id);
    }

//    @PostMapping(value = "/image/user", consumes = "multipart/form-data")
//    public void submitUserImage(@RequestParam("file") MultipartFile file) {
//        imageService.setUserImage();
//    }

}