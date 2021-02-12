package com.example.spaceapi.dto.event;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EventPackageDto {

    private List<EventDto> events;

    private boolean hasEventCreateRights;

}
