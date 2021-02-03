package com.example.spaceapi.dto.mapper;

import com.example.spaceapi.dto.SpacesDto;
import com.example.spaceapi.entity.Event;
import com.example.spaceapi.entity.Space;

import java.util.Comparator;
import java.util.Date;

public class SpacesMapper {

    public static SpacesDto toUserSpacesDto(Space space) {
        SpacesDto dto = new SpacesDto();
        dto.setCode(space.getCode());
        dto.setName(space.getName());

        Date now = new Date();
        space.getEvents().stream()
                .filter(evt -> evt.getDateTime().after(now))
                .min(Comparator.comparing(Event::getDateTime))
                .ifPresent(event -> {
                    SpacesDto.EventBrief brief = new SpacesDto.EventBrief();
                    brief.setNextEventName(event.getName());
                    brief.setNextEventDate(event.getDateTime());
                    dto.setNextEvent(brief);
                });
        return dto;
    }
}
