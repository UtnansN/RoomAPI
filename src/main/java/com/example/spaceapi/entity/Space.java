package com.example.spaceapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Space {

    @Id
    private String code;

    private String name;

    private String description;

    @ManyToMany
    private List<User> members;

    @OneToMany(mappedBy = "space", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Event> events;

}
