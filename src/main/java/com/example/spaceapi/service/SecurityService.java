package com.example.spaceapi.service;

import com.example.spaceapi.controller.SpaceController;
import com.example.spaceapi.entity.Space;
import com.example.spaceapi.entity.UserSpace;
import com.example.spaceapi.exception.SpaceNotFoundException;
import com.example.spaceapi.repository.EventRepository;
import com.example.spaceapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public boolean hasBasicAccessInSpace(String spaceCode) {
        Authentication auth = getAuthentication();
        return userRepository.findUserByEmail(auth.getName())
                .getSpaces().stream()
                .map(UserSpace::getSpace)
                .anyMatch(space -> space.getCode().equals(spaceCode));
    }

    public boolean hasWriteAccessBySpaceCode(String spaceCode) {
        Authentication auth = getAuthentication();
        return userRepository.findUserByEmail(auth.getName())
                .getSpaces().stream()
                .filter(us -> us.getSpace().getCode().equals(spaceCode))
                .findFirst()
                .filter(this::hasWriteAccessByUserSpace)
                .isPresent();
    }

    public boolean hasWriteAccessByUserSpace(UserSpace space) {
        return space.getRole() == UserSpace.SpaceRole.MODERATOR || space.getRole() == UserSpace.SpaceRole.ADMIN;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
