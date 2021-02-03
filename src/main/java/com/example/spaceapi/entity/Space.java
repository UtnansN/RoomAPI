package com.example.spaceapi.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Space {

    @Id
    private String code;

    private String name;

    private String description;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<UserSpace> userSpaces;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<Event> events;

}
