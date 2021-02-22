package com.example.spaceapi.service;

import com.example.spaceapi.dto.user.CreateUserDto;
import com.example.spaceapi.dto.mapper.UserMapper;
import com.example.spaceapi.entity.Image;
import com.example.spaceapi.entity.User;
import com.example.spaceapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.regex.Pattern;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ImageService imageService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) throw new UsernameNotFoundException("Username not found");

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), new ArrayList<>()
        );
    }

    @Transactional
    public void register(CreateUserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email in use");
        }

        if (isValidUserData(userDto)) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User user = userMapper.createUserDtoToUser(userDto);
            userRepository.save(user);

            MultipartFile file = userDto.getImageFile();
            if (file != null) {
                Image image = new Image();
                user.setImage(image);
                imageService.setImageContent(image, userDto.getImageFile());
                userRepository.save(user);
            }
        }
    }

    private boolean isValidUserData(CreateUserDto user) {

        final String emailExpr = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern emailPattern = Pattern.compile(emailExpr);

        if (!emailPattern.matcher(user.getEmail()).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format");
        }

        String password = user.getPassword();
        final String passwordExpr = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
        Pattern passwordPattern = Pattern.compile(passwordExpr);
        if (!passwordPattern.matcher(password).matches() || password.length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Password must be at least 6 characters, where at least one character " +
                            "is a number and one character is a letter");
        }
        return true;
    }

}
