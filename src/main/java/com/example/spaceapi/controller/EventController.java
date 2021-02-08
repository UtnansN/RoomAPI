package com.example.spaceapi.controller;


import com.example.spaceapi.dto.EventDto;
import com.example.spaceapi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/spaces/{spaceCode}/events")
    public List<EventDto> getUpcomingEvents(@PathVariable String spaceCode) {
        return eventService.getUpcomingEvents(spaceCode);
    }

    @GetMapping("/spaces/{spaceCode}/events/past")
    public List<EventDto> getPastEvents(@PathVariable String spaceCode) {
        return eventService.getPastEvents(spaceCode);
    }

    @PostMapping("/spaces/{spaceCode}/events")
    public EventDto createEvent(@RequestBody EventDto eventDto, @PathVariable String spaceCode) {
        return eventService.createEvent(eventDto, spaceCode);
    }

    @PutMapping("/spaces/{spaceCode}/events")
    public void updateEvent(@RequestBody EventDto eventDto, @PathVariable String spaceCode) {
        eventService.alterEvent(eventDto, spaceCode);
    }
}
