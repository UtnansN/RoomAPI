package com.example.roomapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Room {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private String roomCode;

    @ManyToMany
    private List<User> members;

    @OneToMany
    private List<Event> events;
}
