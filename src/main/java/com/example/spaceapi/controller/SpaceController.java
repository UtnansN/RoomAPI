package com.example.spaceapi.controller;


import com.example.spaceapi.dto.space.CreateSpaceDto;
import com.example.spaceapi.dto.space.SpaceBaseDto;
import com.example.spaceapi.dto.space.SpaceInformationDto;
import com.example.spaceapi.dto.space.SpaceBriefDto;
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
    public List<SpaceBriefDto> getSpaces() {
        return spaceService.getSpacesForUser();
    }

    @PutMapping("/{code}/join")
    public SpaceBaseDto joinSpace(@PathVariable String code) {
        return spaceService.addUserToSpace(code);
    }

    @PutMapping("/{code}/leave")
    public void leaveSpace(@PathVariable String code) {
        spaceService.leaveSpace(code);
    }

    @PostMapping
    public SpaceBaseDto createSpace(@RequestBody CreateSpaceDto spaceDto) {
        return spaceService.createSpace(spaceDto);
    }

    @GetMapping("/{code}")
    public SpaceInformationDto getSpace(@PathVariable String code) {
        return spaceService.getSpaceByCode(code);
    }

    @DeleteMapping("/{code}")
    public void deleteSpace(@PathVariable String code) {
        spaceService.deleteSpace(code);
    }
}
