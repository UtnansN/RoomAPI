package com.example.spaceapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Image {

    private @Id @GeneratedValue Long id;

    private Instant lastUpdated;

    private @ContentId UUID contentId;
    private @ContentLength Long contentLength;

}
