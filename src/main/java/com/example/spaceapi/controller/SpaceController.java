package com.example.spaceapi.controller;


import com.example.spaceapi.dto.CreateSpaceDto;
import com.example.spaceapi.dto.UserSpacesDto;
import com.example.spaceapi.entity.Space;
import com.example.spaceapi.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping("/spaces")
    public List<Space> getRooms() {
        return spaceService.getSpaces();
    }

    @GetMapping("/spaces/{code}")
    public UserSpacesDto getUserRooms(@PathVariable String code) {
        return spaceService.getSpaceByCode(code);
    }

    @PostMapping("/spaces")
    public Space addRoom(@RequestBody CreateSpaceDto spaceDto) {
        return spaceService.createSpace(spaceDto);
    }

}
