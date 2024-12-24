package com.example.my_course.repository;

import com.example.my_course.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer,Long> {
}
