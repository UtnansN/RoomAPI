package com.example.spaceapi.service;


import com.example.spaceapi.dto.CreateSpaceDto;
import com.example.spaceapi.dto.mapper.SpaceMapper;
import com.example.spaceapi.utils.SpaceBriefDtoComparator;
import com.example.spaceapi.dto.SpaceInformationDto;
import com.example.spaceapi.dto.SpaceBriefDto;
import com.example.spaceapi.entity.Space;
import com.example.spaceapi.entity.User;
import com.example.spaceapi.entity.UserSpace;
import com.example.spaceapi.exception.SpaceNotFoundException;
import com.example.spaceapi.repository.SpaceRepository;
import com.example.spaceapi.repository.UserRepository;
import com.example.spaceapi.repository.UserSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SpaceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private UserSpaceRepository userSpaceRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SpaceMapper spaceMapper;

    public List<SpaceBriefDto> getSpacesForUser() {
        User user = userRepository.findUserByEmail(securityService.getAuthentication().getName());

        return user.getSpaces().stream()
                .map(UserSpace::getSpace)
                .map(spaceMapper::fromSpaceToSpaceBriefDto)
                .sorted(new SpaceBriefDtoComparator())
                .collect(Collectors.toList());
    }

    @PreAuthorize("@securityService.hasBasicAccessInSpace(#code)")
    public SpaceInformationDto getSpaceByCode(String code) {
        Space space = spaceRepository.findById(code).orElseThrow();
        return spaceMapper.fromSpacetoSpaceInformationDto(space);
    }

    @Transactional
    public Space createSpace(CreateSpaceDto createSpaceDto) {

        Space space = spaceMapper.fromCreateSpaceDtoToSpace(createSpaceDto);

        while (true) {
            String generated = createRandomRoomCode();
            if (spaceRepository.findById(generated).isEmpty()) {
                space.setCode(generated);
                break;
            }
        }

        spaceRepository.save(space);
        addUserToSpace(space, UserSpace.SpaceRole.ADMIN);
        return space;
    }

    public void addUserToSpace(String code) {
        Space space = spaceRepository.findById(code).orElseThrow(SpaceNotFoundException::new);
        addUserToSpace(space, UserSpace.SpaceRole.BASE);
    }

    private void addUserToSpace(Space space, UserSpace.SpaceRole role) {
        User user = userRepository.findUserByEmail(securityService.getAuthentication().getName());

        UserSpace userSpace = new UserSpace(user, space, role);
        try {
            userSpaceRepository.save(userSpace);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    // Birthday problem. Possible id collisions will grow in amount as room count grows
    // making it not viable for room codes such short length.
    // I don't have to worry about it because this is a toy app :)
    private String createRandomRoomCode() {
        final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        final int ROOM_CODE_LENGTH = 6;

        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < ROOM_CODE_LENGTH; i++) {
            str.append(CHARS.charAt((int) (random.nextFloat() * CHARS.length())));
        }
        return str.toString();
    }

}
