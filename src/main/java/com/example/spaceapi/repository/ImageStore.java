package com.example.spaceapi.repository;

import com.example.spaceapi.entity.Image;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface ImageStore extends ContentStore<Image, UUID> {
}
