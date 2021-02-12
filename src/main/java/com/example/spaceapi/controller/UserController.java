package com.example.spaceapi.controller;

import com.example.spaceapi.dto.user.CreateUserDto;
import com.example.spaceapi.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signup")
    public void register(@RequestBody CreateUserDto userDto) {
        userDetailsService.register(userDto);
    }

    // Login handled by filter.

}
