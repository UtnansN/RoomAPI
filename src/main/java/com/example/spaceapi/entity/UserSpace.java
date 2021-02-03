package com.example.spaceapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
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
