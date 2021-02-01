package com.example.roomapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
@Entity
public class Event {

    @Id
    private Long id;

    private String name;

    private String description;

    private String location;

    private String time;

    @ManyToOne
    private Room room;

    @ManyToMany
    private List<User> attendees;

}
