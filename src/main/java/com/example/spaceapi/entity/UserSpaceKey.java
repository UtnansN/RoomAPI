package com.example.spaceapi.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserSpaceKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "space_code")
    String spaceCode;

}
