package com.example.spaceapi.dto.user;

import lombok.Data;

import java.time.Instant;

@Data
public class UserDto {

    private String id;

    private String fullName;

    private Instant joinDate;

    private String role;

    private String imageURI;
}
