package com.example.spaceapi.dto.mapper;

import com.example.spaceapi.dto.event.NextEventBrief;
import com.example.spaceapi.dto.space.CreateSpaceDto;
import com.example.spaceapi.dto.space.SpaceBaseDto;
import com.example.spaceapi.dto.space.SpaceBriefDto;
import com.example.spaceapi.dto.space.SpaceInformationDto;
import com.example.spaceapi.entity.Event;
import com.example.spaceapi.entity.Space;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface SpaceMapper {

    Space fromCreateSpaceDtoToSpace(CreateSpaceDto dto);

    SpaceBaseDto fromSpaceToSpaceBaseDto(Space space);

    @Mapping(source = "space.events", target = "nextEvent", qualifiedByName = "extractNextEventInformation")
    SpaceBriefDto fromSpaceToSpaceBriefDto(Space space);

    @Mapping(source = "space.users", target = "members")
    SpaceInformationDto fromSpaceToSpaceInformationDto(Space space);

    @Named("extractNextEventInformation")
    static NextEventBrief extractNextEventInformation(Set<Event> events) {
        Instant now = Instant.now();

        Optional<Event> eventOptional = events.stream()
                .filter(evt -> evt.getDateTime().isAfter(now))
                .min(Comparator.comparing(Event::getDateTime));

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            NextEventBrief brief = new NextEventBrief();
            brief.setNextEventName(event.getName());
            brief.setNextEventDate(event.getDateTime());
            return brief;
        } else {
            return null;
        }
    }

}
