package com.example.spaceapi.service;

import com.example.spaceapi.dto.EventDto;
import com.example.spaceapi.dto.EventPackageDto;
import com.example.spaceapi.dto.mapper.EventMapper;
import com.example.spaceapi.entity.Event;
import com.example.spaceapi.entity.Space;
import com.example.spaceapi.exception.SpaceNotFoundException;
import com.example.spaceapi.repository.EventRepository;
import com.example.spaceapi.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private EventMapper eventMapper;

    @PreAuthorize("@securityService.hasBasicAccessInSpace(#spaceCode)")
    public EventPackageDto getUpcomingEvents(String spaceCode) {
        Space space = spaceRepository.findById(spaceCode).orElseThrow(SpaceNotFoundException::new);

        List<EventDto> events = space.getEvents().stream()
                .filter(event -> event.getDateTime().isAfter(Instant.now()))
                .sorted(Comparator.comparing(Event::getDateTime))
                .map(eventMapper::eventToEventDto)
                .collect(Collectors.toList());

        return EventPackageDto.builder()
                .events(events)
                .hasEventCreateRights(securityService.hasWriteAccessBySpaceCode(spaceCode))
                .build();
    }

    @PreAuthorize("@securityService.hasBasicAccessInSpace(#spaceCode)")
    public List<EventDto> getPastEvents(String spaceCode) {
        Space space = spaceRepository.findById(spaceCode).orElseThrow(SpaceNotFoundException::new);

        return space.getEvents().stream()
                .filter(event -> event.getDateTime().isBefore(Instant.now()))
                .sorted(Comparator.comparing(Event::getDateTime).reversed())
                .map(eventMapper::eventToEventDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("@securityService.hasWriteAccessBySpaceCode(#spaceCode)")
    public EventDto createEvent(EventDto eventDto, String spaceCode) {
        Space space = spaceRepository.findById(spaceCode).orElseThrow(SpaceNotFoundException::new);
        Event event = eventMapper.eventDtoToEvent(eventDto);

        event.setSpace(space);
        return eventMapper.eventToEventDto(eventRepository.save(event));
    }

    @PreAuthorize("@securityService.hasWriteAccessBySpaceCode(#spaceCode)")
    public void alterEvent(EventDto eventDto, String spaceCode) {
        Event event = eventRepository.findById(eventDto.getEventId()).orElseThrow();
        eventMapper.updateEventFromDto(eventDto, event);


    }

}
