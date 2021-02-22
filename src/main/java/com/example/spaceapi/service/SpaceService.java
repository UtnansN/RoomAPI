package com.example.spaceapi.service;


import com.example.spaceapi.dto.space.CreateSpaceDto;
import com.example.spaceapi.dto.mapper.SpaceMapper;
import com.example.spaceapi.dto.space.SpaceBaseDto;
import com.example.spaceapi.entity.UserSpaceKey;
import com.example.spaceapi.repository.AttendeeRepository;
import com.example.spaceapi.utils.SpaceBriefDtoComparator;
import com.example.spaceapi.dto.space.SpaceInformationDto;
import com.example.spaceapi.dto.space.SpaceBriefDto;
import com.example.spaceapi.entity.Space;
import com.example.spaceapi.entity.User;
import com.example.spaceapi.entity.UserSpace;
import com.example.spaceapi.exception.SpaceNotFoundException;
import com.example.spaceapi.repository.SpaceRepository;
import com.example.spaceapi.repository.UserRepository;
import com.example.spaceapi.repository.UserSpaceRepository;
import com.sun.jdi.request.InvalidRequestStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
    private AttendeeRepository attendeeRepository;

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

    @PreAuthorize("@securityService.hasBasicAccessBySpaceCode(#code)")
    public SpaceInformationDto getSpaceByCode(String code) {
        Space space = spaceRepository.findById(code).orElseThrow();
        return spaceMapper.fromSpaceToSpaceInformationDto(space);
    }

    @Transactional
    public SpaceBaseDto createSpace(CreateSpaceDto createSpaceDto) {

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
        return spaceMapper.fromSpaceToSpaceBaseDto(space);
    }

    public SpaceBaseDto addUserToSpace(String code) {
        Space space = spaceRepository.findById(code).orElseThrow(SpaceNotFoundException::new);
        addUserToSpace(space, UserSpace.SpaceRole.BASE);
        return spaceMapper.fromSpaceToSpaceBaseDto(space);
    }

    private void addUserToSpace(Space space, UserSpace.SpaceRole role) {
        User user = securityService.getUser();

        UserSpace userSpace = new UserSpace(user, space, role);
        userSpaceRepository.save(userSpace);
    }

    @Transactional
    @PreAuthorize("@securityService.hasBasicAccessBySpaceCode(#code)")
    public void leaveSpace(String code) {
        User user = securityService.getUser();

        UserSpace leaver = userSpaceRepository.findById(new UserSpaceKey(user.getId(), code))
                .orElseThrow(InvalidRequestStateException::new);

        attendeeRepository.deleteAttendeesByUserAndEvent_SpaceAndEvent_DateTimeIsAfter(user, leaver.getSpace(), Instant.now());

        // If person leaving has the ADMIN role, migrate it to a random moderator or, if none exist,
        // anyone else present in the space. If room becomes empty on leave, delete it.
        // Else just delete link between space and user.
        if (leaver.getRole() == UserSpace.SpaceRole.ADMIN) {

            List<UserSpace> otherUserSpaces = userSpaceRepository
                    .findUserSpacesBySpace(leaver.getSpace())
                    .stream()
                    .filter(us -> us != leaver)
                    .collect(Collectors.toList());
            if (otherUserSpaces.isEmpty()) {
                spaceRepository.deleteById(code);
                return;
            }

            Optional<UserSpace> optionalModerator = otherUserSpaces.stream()
                    .filter(us -> us.getRole() == UserSpace.SpaceRole.MODERATOR)
                    .findAny();

            UserSpace candidate = optionalModerator
                    .orElseGet(() -> otherUserSpaces.stream()
                            .findAny()
                            .orElseThrow(InvalidRequestStateException::new));
            candidate.setRole(UserSpace.SpaceRole.ADMIN);
            userSpaceRepository.save(candidate);
        }
        userSpaceRepository.delete(leaver);
    }

    @PreAuthorize("@securityService.hasAdminAccessInSpace(#code)")
    public void deleteSpace(String code) {
        spaceRepository.deleteById(code);
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
