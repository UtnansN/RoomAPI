package com.example.spaceapi.repository;

import com.example.spaceapi.entity.UserSpace;
import com.example.spaceapi.entity.UserSpaceKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSpaceRepository extends JpaRepository<UserSpace, UserSpaceKey> {
}
