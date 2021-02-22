package com.example.spaceapi.service;

import com.example.spaceapi.dto.event.EventBriefDto;
import com.example.spaceapi.dto.event.EventExtendedDto;
import com.example.spaceapi.dto.event.EventPackageDto;
import com.example.spaceapi.dto.mapper.EventMapper;
import com.example.spaceapi.entity.*;
import com.example.spaceapi.exception.SpaceNotFoundException;
import com.example.spaceapi.repository.AttendeeRepository;
import com.example.spaceapi.repository.EventRepository;
import com.example.spaceapi.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Comparator;
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
    private AttendeeRepository attendeeRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private EventMapper eventMapper;

    @PreAuthorize("@securityService.hasBasicAccessBySpaceCode(#spaceCode)")
    public EventPackageDto getUpcomingEvents(String spaceCode) {
        Space space = spaceRepository.findById(spaceCode).orElseThrow(SpaceNotFoundException::new);

        List<EventBriefDto> events = space.getEvents().stream()
                .filter(event -> event.getDateTime().isAfter(Instant.now()))
                .sorted(Comparator.comparing(Event::getDateTime))
                .map(eventMapper::eventToEventBriefDto)
                .collect(Collectors.toList());

        return EventPackageDto.builder()
                .events(events)
                .hasEventCreateRights(securityService.hasWriteAccessBySpaceCode(spaceCode))
                .build();
    }

    @PreAuthorize("@securityService.hasBasicAccessBySpaceCode(#spaceCode)")
    public List<EventBriefDto> getPastEvents(String spaceCode) {
        Space space = spaceRepository.findById(spaceCode).orElseThrow(SpaceNotFoundException::new);

        return space.getEvents().stream()
                .filter(event -> event.getDateTime().isBefore(Instant.now()))
                .sorted(Comparator.comparing(Event::getDateTime).reversed())
                .map(eventMapper::eventToEventBriefDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @PreAuthorize("@securityService.hasWriteAccessBySpaceCode(#spaceCode)")
    public EventBriefDto createEvent(EventBriefDto eventDto, String spaceCode) {
        Space space = spaceRepository.findById(spaceCode).orElseThrow(SpaceNotFoundException::new);
        Event event = eventMapper.eventDtoToEvent(eventDto);
        User user = securityService.getUser();

        event.setSpace(space);
        event = eventRepository.save(event);
        attendeeRepository.save(new Attendee(user, event));

        return eventMapper.eventToEventBriefDto(event);
    }

    @PreAuthorize("@securityService.hasWriteAccessBySpaceCode(#spaceCode)")
    public void alterEvent(EventBriefDto eventDto, String spaceCode) {
        Event event = eventRepository.findEventByIdAndSpace_Code(eventDto.getEventId(), spaceCode)
                .orElseThrow();
        eventMapper.updateEventFromDto(eventDto, event);
    }

    @PreAuthorize("@securityService.hasBasicAccessBySpaceCode(#spaceCode)")
    public EventExtendedDto getEventExtendedInformation(String spaceCode, Long id) {
        User user = securityService.getUser();
        Event event = eventRepository.findEventByIdAndSpace_Code(id, spaceCode).orElseThrow();
        boolean userAttending = attendeeRepository.existsById(new AttendeeKey(id, user.getId()));

        EventExtendedDto dto = eventMapper.eventToEventExtendedDto(event);
        dto.setCurrentUserAttending(userAttending);
        return dto;
    }

    @PreAuthorize("@securityService.hasBasicAccessBySpaceCode(#spaceCode)")
    public void attendEvent(String spaceCode, Long id) {
        User user = securityService.getUser();
        Event event = eventRepository.findEventByIdAndSpace_Code(id, spaceCode).orElseThrow();

        Set<Attendee> attendees = event.getAttendees();
        // If there is a max attendee count, check if adding user would make it go over.
        if (event.getMaxAttendees() != 0 && attendees.size() >= event.getMaxAttendees()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Maximum attendee count reached");
        }
        boolean containsUser = attendees.stream()
                .anyMatch(attendee -> attendee.getUser().equals(user));

        if (!containsUser) {
            Attendee attendee = new Attendee(user, event);
            attendeeRepository.save(attendee);
        }
    }

    @PreAuthorize("@securityService.hasBasicAccessBySpaceCode(#spaceCode)")
    public void unattendEvent(String spaceCode, Long id) {
        User user = securityService.getUser();
        attendeeRepository.findById(new AttendeeKey(id, user.getId()))
            .ifPresent(attendeeRepository::delete);
    }
}
