package com.example.my_course.service;

import com.example.my_course.entity.Course;
import com.example.my_course.entity.CourseSummary;
import com.example.my_course.repository.CourseRepository;
import com.example.my_course.repository.CourseSummaryRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseSummaryService {
    private final CourseRepository courseRepository;
    private final CourseSummaryRepository courseSummaryRepository;
    public CourseSummaryService(CourseRepository courseRepository, CourseSummaryRepository courseSummaryRepository) {
        this.courseRepository = courseRepository;
        this.courseSummaryRepository = courseSummaryRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void saveCourseSummary() {

        List<Course> courses = courseRepository.findAll();

        //temporary not delete will repeat
        //find method to avoid repeat instead of del all
        //reason - id will keep changing
        courseSummaryRepository.deleteAll();

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            CourseSummary courseSummary = new CourseSummary();
            courseSummary.setLecturerId(course.getLecturer() != null ? course.getLecturer().getLecturerId() : 0L);
            courseSummary.setCourseId(course.getCourseId());
            courseSummary.setLatestUpdateTimestamp(LocalDateTime.now()); // Set current date and time
            courseSummaryRepository.save(courseSummary);
        }
    }
}
