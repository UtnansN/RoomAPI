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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private Set<UserSpace> users;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private Set<Event> events;

}
