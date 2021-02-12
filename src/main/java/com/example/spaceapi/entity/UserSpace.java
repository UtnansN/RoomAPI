package com.example.spaceapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserSpace {

    public enum SpaceRole {
        ADMIN,
        MODERATOR,
        BASE
    }

    public UserSpace(User u, Space s, SpaceRole r) {
        this.id = new UserSpaceKey();

        this.user = u;
        this.space = s;
        this.role = r;
    }

    @EmbeddedId
    private UserSpaceKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("spaceCode")
    @JoinColumn(name = "space_code")
    private Space space;

    private SpaceRole role;

}
