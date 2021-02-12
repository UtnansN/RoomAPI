package com.example.spaceapi.dto.event;

import lombok.Data;

import java.time.Instant;

@Data
public class NextEventBrief {

    private String nextEventName;

    private Instant nextEventDate;

}
