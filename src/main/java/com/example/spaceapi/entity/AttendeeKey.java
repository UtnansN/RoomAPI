package com.example.spaceapi.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeKey implements Serializable {

    @Column(name = "event_id")
    Long eventId;

    @Column(name = "user_id")
    Long userId;

}
