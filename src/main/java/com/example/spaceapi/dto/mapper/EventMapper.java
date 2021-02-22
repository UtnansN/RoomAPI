package com.example.spaceapi.dto.mapper;

import com.example.spaceapi.dto.event.EventBriefDto;
import com.example.spaceapi.dto.event.EventCommentDto;
import com.example.spaceapi.dto.event.EventExtendedDto;
import com.example.spaceapi.entity.Event;
import com.example.spaceapi.entity.EventComment;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface EventMapper {

    @Mapping(target = "eventId", source = "entity.id")
    EventBriefDto eventToEventBriefDto(Event entity);

    @Mapping(target = "id", source = "dto.eventId")
    Event eventDtoToEvent(EventBriefDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(EventBriefDto dto, @MappingTarget Event entity);

    EventExtendedDto eventToEventExtendedDto(Event entity);

    @Mapping(target = "commentId", source = "entity.id")
    EventCommentDto commentToEventCommentDto(EventComment entity);
}
