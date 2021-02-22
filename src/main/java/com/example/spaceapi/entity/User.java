package com.example.spaceapi.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserSpace> spaces;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Attendee> attendances;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
