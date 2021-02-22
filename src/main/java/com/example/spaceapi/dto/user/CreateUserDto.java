package com.example.spaceapi.dto.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateUserDto {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private MultipartFile imageFile;
}
