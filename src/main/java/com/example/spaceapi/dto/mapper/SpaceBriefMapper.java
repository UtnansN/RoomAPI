package com.example.spaceapi.dto.mapper;

import com.example.spaceapi.dto.SpaceBriefDto;
import com.example.spaceapi.entity.Event;
import com.example.spaceapi.entity.Space;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;

public class SpaceBriefMapper {

    public static SpaceBriefDto toUserSpacesDto(Space space) {
        SpaceBriefDto dto = new SpaceBriefDto();
        dto.setCode(space.getCode());
        dto.setName(space.getName());

        Instant now = Instant.now();
        space.getEvents().stream()
                .filter(evt -> evt.getDateTime().isAfter(now))
                .min(Comparator.comparing(Event::getDateTime))
                .ifPresent(event -> {
                    SpaceBriefDto.EventBrief brief = new SpaceBriefDto.EventBrief();
                    brief.setNextEventName(event.getName());
                    brief.setNextEventDate(event.getDateTime());
                    dto.setNextEvent(brief);
                });

        return dto;
    }
}
