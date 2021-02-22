package com.example.spaceapi.dto.event;

import com.example.spaceapi.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class EventExtendedDto {

    private boolean currentUserAttending;

    // 0 means unlimited.
    private int maxAttendees;

    private List<UserDto> attendees;

    private List<EventCommentDto> comments;

}
