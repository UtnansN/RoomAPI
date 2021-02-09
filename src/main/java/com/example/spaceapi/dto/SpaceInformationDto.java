package com.example.spaceapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class SpaceInformationDto {

    private String code;

    private String name;

    private String description;

    private List<UserDto> members;
}
