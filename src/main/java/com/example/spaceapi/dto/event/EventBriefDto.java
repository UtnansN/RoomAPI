package com.example.spaceapi.dto.event;

import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class EventBriefDto {

    private Long eventId;

    private String name;

    private String description;

    private String dateTime;

    private String location;

}
