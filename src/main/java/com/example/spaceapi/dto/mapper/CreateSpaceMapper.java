package com.example.spaceapi.dto.mapper;


import com.example.spaceapi.dto.CreateSpaceDto;
import com.example.spaceapi.entity.Space;

public class CreateSpaceMapper {

    public static Space toSpace(CreateSpaceDto dto) {
        Space space = new Space();
        space.setName(dto.getName());
        space.setDescription(dto.getDescription());
        return space;
    }

}
