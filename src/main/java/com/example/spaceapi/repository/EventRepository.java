package com.example.spaceapi.repository;

import com.example.spaceapi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findEventByIdAndSpace_Code(Long eventId, String spaceCode);

}
