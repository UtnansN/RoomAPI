package com.example.spaceapi.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class UserSpace implements Serializable {

    public enum SpaceRole {
        ADMIN,
        MODERATOR,
        BASE
    }

    @Id
    @ManyToOne
    private User user;

    @Id
    @ManyToOne
    private Space space;

    private SpaceRole role;

}
