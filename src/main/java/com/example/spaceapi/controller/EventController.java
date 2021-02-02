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
    public List<EventDto> getRoomEvents(@PathVariable String spaceCode) {
        return eventService.getEvents(spaceCode);
    }

    @PostMapping("/spaces/{spaceCode}/event")
    public EventDto createEvent(@RequestBody EventDto eventDto, @PathVariable String spaceCode) {
        return eventService.createEvent(eventDto, spaceCode);
    }

    @PutMapping("/event")
    public void updateEvent(@RequestBody EventDto eventDto) {
        eventService.alterEvent(eventDto);
    }
}
