package com.example.my_course.repository;

import com.example.my_course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByCourseId(Long courseId);
}
