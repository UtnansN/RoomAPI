package com.example.spaceapi.dto.mapper;

import com.example.spaceapi.dto.SpaceInformationDto;
import com.example.spaceapi.entity.Space;

public class SpaceInformationMapper {

    public static SpaceInformationDto toDto(Space space) {
        SpaceInformationDto dto = new SpaceInformationDto();
        dto.setCode(space.getCode());
        dto.setName(space.getName());
        dto.setDescription(space.getDescription());
        dto.setMemberCount(space.getMembers().size());
        return dto;
    }

}
