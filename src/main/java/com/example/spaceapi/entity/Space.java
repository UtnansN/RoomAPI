package com.example.spaceapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Space {

    @Id
    private String spaceCode;

    private String name;

    private String description;

    @ManyToMany
    private List<User> members;

    @OneToMany
    private List<Event> events;
}
