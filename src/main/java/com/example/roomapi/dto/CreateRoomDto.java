package com.example.roomapi.dto;


import lombok.Data;

@Data
public class CreateRoomDto {

    private String id;

    private String name;

    private String description;

    private String roomCode;

}
