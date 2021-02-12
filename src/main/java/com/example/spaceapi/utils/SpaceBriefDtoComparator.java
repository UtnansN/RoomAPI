package com.example.spaceapi.utils;

import com.example.spaceapi.dto.event.NextEventBrief;
import com.example.spaceapi.dto.space.SpaceBriefDto;

import java.time.Instant;
import java.util.Comparator;

public class SpaceBriefDtoComparator implements Comparator<SpaceBriefDto> {

    @Override
    public int compare(SpaceBriefDto o1, SpaceBriefDto o2) {
        NextEventBrief eb1 = o1.getNextEvent();
        NextEventBrief eb2 = o2.getNextEvent();

        if (eb1 != null && eb2 != null) {

            Instant i1 = eb1.getNextEventDate();
            Instant i2 = eb2.getNextEventDate();

            int result = i1.compareTo(i2);

            if (result == 0) {
                return o1.getName().compareTo(o2.getName());
            } else {
                return result;
            }

        } else if (eb1 == null && eb2 == null) {
            return o1.getName().compareTo(o2.getName());
        } else if (eb1 == null) {
            return 1;
        } else {
            return -1;
        }
    }

}
