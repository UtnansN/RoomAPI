package com.example.spaceapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
public class EventComment {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Instant creationDate;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    private User author;

    @ManyToOne
    private Event event;

}
