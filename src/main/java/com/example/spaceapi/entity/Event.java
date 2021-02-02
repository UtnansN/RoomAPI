package com.example.spaceapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Event {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String location;

    @Column(nullable = false)
    private Date dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Space space;

    @ManyToMany
    private List<User> attendees;

}
