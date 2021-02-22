package com.example.spaceapi.controller;

import com.example.spaceapi.dto.user.CreateUserDto;
import com.example.spaceapi.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // Login handled by filter.

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void register(@ModelAttribute CreateUserDto createUserDto) {
        userDetailsService.register(createUserDto);
    }

}
