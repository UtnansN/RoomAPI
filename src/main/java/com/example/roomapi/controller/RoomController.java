package com.example.roomapi.controller;


import com.example.roomapi.dto.CreateRoomDto;
import com.example.roomapi.dto.UserRoomsDto;
import com.example.roomapi.entity.Room;
import com.example.roomapi.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public List<Room> getRooms() {
        return roomService.getRooms();
    }

    @GetMapping("/rooms/{code}")
    public UserRoomsDto getUserRooms(@PathVariable String code) {
        return roomService.getRoomByCode(code);
    }

    @PostMapping("/rooms")
    public Room addRoom(@RequestBody CreateRoomDto roomDto) {
        return roomService.createRoom(roomDto);
    }

}
