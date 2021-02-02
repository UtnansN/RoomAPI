package com.example.spaceapi.service;

import com.example.spaceapi.dto.EventDto;
import com.example.spaceapi.dto.mapper.EventMapper;
import com.example.spaceapi.entity.Event;
import com.example.spaceapi.entity.Space;
import com.example.spaceapi.exception.SpaceNotFoundException;
import com.example.spaceapi.repository.EventRepository;
import com.example.spaceapi.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    public List<EventDto> getEvents(String spaceCode) {
        Space space = spaceRepository.findById(spaceCode).orElseThrow(SpaceNotFoundException::new);

        Date now = new Date();
        return space.getEvents().stream()
                .filter(event -> event.getDateTime().after(now))
                .sorted(Comparator.comparing(Event::getDateTime))
                .map(eventMapper::eventToEventDto)
                .collect(Collectors.toList());
    }

    public void alterEvent(EventDto eventDto) {
        Event event = eventRepository.getOne(eventDto.getEventId());
        eventMapper.updateEventFromDto(eventDto, event);
        eventRepository.save(event);
    }

    public EventDto createEvent(EventDto eventDto, String roomCode) {
        Space space = spaceRepository.findById(roomCode).orElseThrow(SpaceNotFoundException::new);
        Event event = eventMapper.eventDtoToEvent(eventDto);

        event.setSpace(space);
        return eventMapper.eventToEventDto(eventRepository.save(event));
    }

}
