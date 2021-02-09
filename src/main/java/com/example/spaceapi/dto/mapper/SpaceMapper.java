package com.example.spaceapi.dto.mapper;

import com.example.spaceapi.dto.CreateSpaceDto;
import com.example.spaceapi.dto.SpaceBriefDto;
import com.example.spaceapi.dto.SpaceInformationDto;
import com.example.spaceapi.entity.Event;
import com.example.spaceapi.entity.Space;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface SpaceMapper {

    Space fromCreateSpaceDtoToSpace(CreateSpaceDto dto);

    @Mapping(source = "space.events", target = "nextEvent", qualifiedByName = "extractNextEventInformation")
    SpaceBriefDto fromSpaceToSpaceBriefDto(Space space);

    SpaceInformationDto fromSpacetoSpaceInformationDto(Space space);

    @Named("extractNextEventInformation")
    static SpaceBriefDto.EventBrief extractNextEventInformation(Set<Event> events) {
        Instant now = Instant.now();

        Optional<Event> eventOptional = events.stream()
                .filter(evt -> evt.getDateTime().isAfter(now))
                .min(Comparator.comparing(Event::getDateTime));

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            SpaceBriefDto.EventBrief brief = new SpaceBriefDto.EventBrief();
            brief.setNextEventName(event.getName());
            brief.setNextEventDate(event.getDateTime());
            return brief;
        } else {
            return null;
        }
    }

}
