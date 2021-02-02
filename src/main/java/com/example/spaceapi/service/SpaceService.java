package com.example.spaceapi.service;


import com.example.spaceapi.dto.CreateSpaceDto;
import com.example.spaceapi.dto.SpaceInformationDto;
import com.example.spaceapi.dto.UserSpacesDto;
import com.example.spaceapi.dto.mapper.CreateSpaceMapper;
import com.example.spaceapi.dto.mapper.SpaceInformationMapper;
import com.example.spaceapi.dto.mapper.UserSpacesMapper;
import com.example.spaceapi.entity.Space;
import com.example.spaceapi.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    public List<UserSpacesDto> getSpaces() {
        List<Space> userSpaces = spaceRepository.findAll();

        return userSpaces.stream()
                .map(UserSpacesMapper::toUserSpacesDto)
                .collect(Collectors.toList());
    }

    public SpaceInformationDto getSpaceByCode(String code) {
        Space space = spaceRepository.findById(code).orElseThrow();
        return SpaceInformationMapper.toDto(space);
    }

    public Space createSpace(CreateSpaceDto createSpaceDto) {

        Space space = CreateSpaceMapper.toSpace(createSpaceDto);

        while (true) {
            String generated = createRandomRoomCode();
            if (spaceRepository.findById(generated).isEmpty()) {
                space.setCode(generated);
                break;
            }
        }

        return spaceRepository.save(space);
    }

    // Birthday problem. Possible id collisions will grow in amount as room count grows
    // making it not viable for room codes such short length.
    // I don't have to worry about it because this is a toy app :)
    private String createRandomRoomCode() {
        final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(CHARS.charAt((int) (random.nextFloat() * 36)));
        }
        return str.toString();
    }

}
