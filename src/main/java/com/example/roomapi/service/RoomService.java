package com.example.roomapi.service;


import com.example.roomapi.dto.CreateRoomDto;
import com.example.roomapi.dto.UserRoomsDto;
import com.example.roomapi.dto.mapper.CreateRoomMapper;
import com.example.roomapi.entity.Room;
import com.example.roomapi.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public UserRoomsDto getRoomByCode(String code) {
        return null;
    }

    public Room createRoom(CreateRoomDto createRoomDto) {
        Room room = CreateRoomMapper.toRoom(createRoomDto);
        return roomRepository.save(room);
    }

}
