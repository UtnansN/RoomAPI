package com.example.spaceapi.dto.mapper;

import com.example.spaceapi.dto.UserSpacesDto;
import com.example.spaceapi.entity.Event;
import com.example.spaceapi.entity.Space;

import java.util.Comparator;
import java.util.Date;

public class UserSpacesMapper {

    public static UserSpacesDto toUserSpacesDto(Space space) {
        UserSpacesDto dto = new UserSpacesDto();
        dto.setCode(space.getCode());
        dto.setName(space.getName());

        Date now = new Date();
        space.getEvents().stream()
                .filter(evt -> evt.getDateTime().after(now))
                .min(Comparator.comparing(Event::getDateTime))
                .ifPresent(event -> {
                    UserSpacesDto.EventBrief brief = new UserSpacesDto.EventBrief();
                    brief.setNextEventName(event.getName());
                    brief.setNextEventDate(event.getDateTime());
                    dto.setNextEvent(brief);
                });
        return dto;
    }
}
