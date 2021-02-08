package com.example.spaceapi.dto;

import lombok.Data;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;

@Data
public class SpaceBriefDto {

    @Data
    public static class EventBrief {

        private String nextEventName;

        private Instant nextEventDate;

    }

    private String code;

    private String name;

    private EventBrief nextEvent;

    private boolean hasWriteRights;

}
