package com.example.spaceapi.service;

import com.example.spaceapi.entity.Image;
import com.example.spaceapi.entity.User;
import com.example.spaceapi.repository.ImageRepository;
import com.example.spaceapi.repository.ImageStore;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageStore imageStore;

    public ResponseEntity<byte[]> getImage(String uuid, String jwt) {

        Image image = imageRepository.getImageByContentId(UUID.fromString(uuid));
        InputStream content = imageStore.getContent(image);
        return null;
    }

    public byte[] getImage(Long imageId) {
        Image image = imageRepository.getOne(imageId);
        InputStream content = imageStore.getContent(image);
        try {
            return IOUtils.toByteArray(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setImageContent(Image imageEntity, MultipartFile image) {
        imageEntity.setLastUpdated(Instant.now());
        try {
            imageStore.setContent(imageEntity, image.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getImageResourceString(Image image) {
        return null;
    }

}
