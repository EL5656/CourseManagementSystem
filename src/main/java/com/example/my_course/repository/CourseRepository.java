package com.example.my_course.repository;

import com.example.my_course.entity.Course;
import com.example.my_course.entity.CourseSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
