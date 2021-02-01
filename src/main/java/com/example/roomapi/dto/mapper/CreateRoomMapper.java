package com.example.roomapi.dto.mapper;


import com.example.roomapi.dto.CreateRoomDto;
import com.example.roomapi.entity.Room;

public class CreateRoomMapper {

    public static Room toRoom(CreateRoomDto dto) {
        Room room = new Room();
        room.setName(dto.getName());
        room.setDescription(dto.getDescription());

        return room;
    }

}
