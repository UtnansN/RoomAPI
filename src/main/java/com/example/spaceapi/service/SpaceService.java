package com.example.spaceapi.service;


import com.example.spaceapi.dto.CreateSpaceDto;
import com.example.spaceapi.dto.UserSpacesDto;
import com.example.spaceapi.dto.mapper.CreateSpaceMapper;
import com.example.spaceapi.entity.Space;
import com.example.spaceapi.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    public List<Space> getSpaces() {
        return spaceRepository.findAll();
    }

    public UserSpacesDto getSpaceByCode(String code) {
        return null;
    }

    public Space createSpace(CreateSpaceDto createSpaceDto) {

        Space space = CreateSpaceMapper.toSpace(createSpaceDto);

        while (true) {
            String generated = createRandomRoomCode();
            if (spaceRepository.findById(generated).isEmpty()) {
                space.setSpaceCode(generated);
                break;
            }
        }


        return spaceRepository.save(space);
    }

    // Birthday problem. Possible id collisions will grow in amount as room count grows
    // making it not viable for room codes such short length.
    // I don't have to worry about it because this is a toy app :)
    private String createRandomRoomCode() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(CHARS.charAt((int) (random.nextFloat() * 36)));
        }
        return str.toString();
    }

}
