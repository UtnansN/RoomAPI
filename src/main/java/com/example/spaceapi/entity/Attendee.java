package com.example.spaceapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attendee {

    public Attendee(User u, Event e) {
        this.id = new AttendeeKey();

        this.user = u;
        this.event = e;
        this.joinDate = Instant.now();
    }

    @EmbeddedId
    private AttendeeKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    private Instant joinDate;
}
