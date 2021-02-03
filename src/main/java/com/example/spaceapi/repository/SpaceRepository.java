package com.example.spaceapi.repository;

import com.example.spaceapi.entity.Space;
import com.example.spaceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, String> {

}
