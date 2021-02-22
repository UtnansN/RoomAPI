package com.example.spaceapi.repository;

import com.example.spaceapi.entity.Space;
import com.example.spaceapi.entity.UserSpace;
import com.example.spaceapi.entity.UserSpaceKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSpaceRepository extends JpaRepository<UserSpace, UserSpaceKey> {
    List<UserSpace> findUserSpacesBySpace(Space space);
}
