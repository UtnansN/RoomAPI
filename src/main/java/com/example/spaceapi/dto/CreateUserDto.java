package com.example.spaceapi.dto;

import lombok.Data;

@Data
public class CreateUserDto {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

}
