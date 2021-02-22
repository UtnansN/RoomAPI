package com.example.spaceapi.dto.event;

import com.example.spaceapi.dto.user.UserDto;
import lombok.Data;

import java.time.Instant;

@Data
public class EventCommentDto {

    private Long commentId;

    private UserDto author;

    private Instant date;

    private String text;

}
