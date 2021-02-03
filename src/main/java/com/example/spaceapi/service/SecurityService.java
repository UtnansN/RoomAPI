package com.example.spaceapi.service;

import com.example.spaceapi.entity.Event;
import com.example.spaceapi.entity.UserSpace;
import com.example.spaceapi.repository.EventRepository;
import com.example.spaceapi.repository.SpaceRepository;
import com.example.spaceapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public boolean hasBasicAccessInSpace(String spaceCode) {
        Authentication auth = getAuthentication();
        return userRepository.findUserByEmail(auth.getName())
                .getUserSpaces().stream()
                .map(UserSpace::getSpace)
                .anyMatch(space -> space.getCode().equals(spaceCode));
    }

    public boolean hasWritePermissionInSpace(String spaceCode) {
        Authentication auth = getAuthentication();
        return userRepository.findUserByEmail(auth.getName())
                .getUserSpaces().stream()
                .filter(us -> us.getRole() == UserSpace.SpaceRole.MODERATOR || us.getRole() == UserSpace.SpaceRole.ADMIN)
                .map(UserSpace::getSpace)
                .anyMatch(space -> space.getCode().equals(spaceCode));
    }

    public boolean hasWritePermissionInSpace(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        return hasWritePermissionInSpace(event.getSpace().getCode());
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
