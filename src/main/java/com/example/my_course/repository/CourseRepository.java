package com.example.my_course.repository;

import com.example.my_course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    //fetch data from existing table without saving into db
    @Query("SELECT c.courseId, c.lecturer.lecturerId FROM Course c")
    List<Object[]> findCourseIdAndLecturerId();
}
