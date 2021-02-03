package com.example.spaceapi.controller;


import com.example.spaceapi.dto.CreateSpaceDto;
import com.example.spaceapi.dto.SpaceInformationDto;
import com.example.spaceapi.dto.SpacesDto;
import com.example.spaceapi.entity.Space;
import com.example.spaceapi.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spaces")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping
    public List<SpacesDto> getSpaces() {
        return spaceService.getSpacesForUser();
    }

    @PostMapping
    public Space addSpace(@RequestBody CreateSpaceDto spaceDto) {
        return spaceService.createSpace(spaceDto);
    }

    @GetMapping("/{code}")
    public SpaceInformationDto getSpace(@PathVariable String code) {
        return spaceService.getSpaceByCode(code);
    }

}
