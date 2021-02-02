package com.example.spaceapi.dto.mapper;

import com.example.spaceapi.dto.EventDto;
import com.example.spaceapi.entity.Event;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mappings({@Mapping(target = "eventId", source = "entity.id")})
    EventDto eventToEventDto(Event entity);

    @Mappings({@Mapping(target = "id", source = "dto.eventId")})
    Event eventDtoToEvent(EventDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(EventDto dto, @MappingTarget Event event);

}
