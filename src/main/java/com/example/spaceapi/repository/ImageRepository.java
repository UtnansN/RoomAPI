package com.example.spaceapi.repository;

import com.example.spaceapi.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image getImageByContentId(UUID uuid);

}
