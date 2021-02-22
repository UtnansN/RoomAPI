package com.example.spaceapi.repository;

import com.example.spaceapi.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface AttendeeRepository extends JpaRepository<Attendee, AttendeeKey> {

    void deleteAttendeesByUserAndEvent_SpaceAndEvent_DateTimeIsAfter(User user, Space space, Instant instant);

}
