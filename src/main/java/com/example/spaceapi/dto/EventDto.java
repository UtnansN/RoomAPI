package com.example.spaceapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EventDto {

    private Long eventId;

    private String name;

    private String description;

    private Date dateTime;

    private String location;

}
