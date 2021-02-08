package com.example.spaceapi.dto.mapper;

import com.example.spaceapi.dto.SpaceInformationDto;
import com.example.spaceapi.entity.Space;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpaceInformationMapper {

    SpaceInformationDto toSpaceInformationDto(Space space);

}
