package com.example.spaceapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserSpacesDto {

    @Data
    public static class EventBrief {

        private String nextEventName;

        private Date nextEventDate;

    }

    private String code;

    private String name;

    private EventBrief nextEvent;

}
