package com.example.my_course.repository;

import com.example.my_course.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer,Long> {
    Optional<Lecturer> findByEmail(String email);
    Lecturer findByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);
}
