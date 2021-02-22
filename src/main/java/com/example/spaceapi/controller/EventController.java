package com.example.spaceapi.controller;


import com.example.spaceapi.dto.event.EventBriefDto;
import com.example.spaceapi.dto.event.EventExtendedDto;
import com.example.spaceapi.dto.event.EventPackageDto;
import com.example.spaceapi.dto.user.UserDto;
import com.example.spaceapi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/spaces/{spaceCode}/events")
    public EventPackageDto getUpcomingEvents(@PathVariable String spaceCode) {
        return eventService.getUpcomingEvents(spaceCode);
    }

    @GetMapping("/spaces/{spaceCode}/events/past")
    public List<EventBriefDto> getPastEvents(@PathVariable String spaceCode) {
        return eventService.getPastEvents(spaceCode);
    }

    @PostMapping("/spaces/{spaceCode}/events")
    public EventBriefDto createEvent(@RequestBody EventBriefDto eventDto, @PathVariable String spaceCode) {
        return eventService.createEvent(eventDto, spaceCode);
    }

    @PutMapping("/spaces/{spaceCode}/events")
    public void updateEvent(@RequestBody EventBriefDto eventDto, @PathVariable String spaceCode) {
        eventService.alterEvent(eventDto, spaceCode);
    }

    @GetMapping("/spaces/{spaceCode}/events/{id}")
    public EventExtendedDto getEventExtendedInformation(@PathVariable String spaceCode, @PathVariable Long id) {
        return eventService.getEventExtendedInformation(spaceCode, id);
    }

    @PutMapping("/spaces/{spaceCode}/events/{id}/attend")
    public void attendEvent(@PathVariable String spaceCode, @PathVariable Long id) {
        eventService.attendEvent(spaceCode, id);
    }

    @PutMapping("/spaces/{spaceCode}/events/{id}/unattend")
    public void unattendEvent(@PathVariable String spaceCode, @PathVariable Long id) {
        eventService.unattendEvent(spaceCode, id);
    }

}
