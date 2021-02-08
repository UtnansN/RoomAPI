package com.example.spaceapi.dto;

import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class EventDto {

    private Long eventId;

    private String name;

    private String description;

    private String dateTime;

    private String location;

}
