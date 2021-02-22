package com.example.spaceapi.dto.mapper;

import com.example.spaceapi.dto.user.CreateUserDto;
import com.example.spaceapi.dto.user.UserDto;
import com.example.spaceapi.entity.Attendee;
import com.example.spaceapi.entity.User;
import com.example.spaceapi.entity.UserSpace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User createUserDtoToUser(CreateUserDto dto);

    @Mapping(source = "userSpace.user.id", target = "id")
    @Mapping(source = "userSpace.user.fullName", target = "fullName")
    UserDto userSpaceToUserDto(UserSpace userSpace);

    @Mapping(source = "attendee.user.id", target = "id")
    @Mapping(source = "attendee.user.fullName", target = "fullName")
    UserDto attendeeToUserDto(Attendee attendee);
}
