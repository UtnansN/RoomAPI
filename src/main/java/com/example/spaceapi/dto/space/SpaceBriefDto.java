package com.example.spaceapi.dto.space;

import com.example.spaceapi.dto.event.NextEventBrief;
import lombok.Data;

@Data
public class SpaceBriefDto {

    private String code;

    private String name;

    private NextEventBrief nextEvent;
}
