package com.example.spaceapi.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Event {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String location;

    // 0 means there's no limit on attendee count.
    private Integer maxAttendees;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @Column(nullable = false)
    private Instant dateTime;

    @ManyToOne
    private Space space;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @OrderBy("user.firstName ASC, user.lastName ASC")
    private Set<Attendee> attendees;
}