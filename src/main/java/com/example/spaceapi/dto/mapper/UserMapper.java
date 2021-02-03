package com.example.spaceapi.dto.mapper;

import com.example.spaceapi.dto.CreateUserDto;
import com.example.spaceapi.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User createUserDtoToUser(CreateUserDto dto);

}
