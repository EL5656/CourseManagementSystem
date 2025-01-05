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
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            CourseSummary existingCourseSummary = courseSummaryRepository.findByCourseIdAndLecturerId(course.getCourseId(),
                    course.getLecturer() != null ? course.getLecturer().getLecturerId() : 0L);

            if (existingCourseSummary != null) {
                //update
                existingCourseSummary.setLatestUpdateTimestamp(LocalDateTime.now());
                courseSummaryRepository.save(existingCourseSummary);
            } else {
                CourseSummary courseSummary = new CourseSummary();
                courseSummary.setLecturerId(course.getLecturer() != null ? course.getLecturer().getLecturerId() : 0L);
                courseSummary.setCourseId(course.getCourseId());
                courseSummary.setLatestUpdateTimestamp(LocalDateTime.now());
                courseSummaryRepository.save(courseSummary);
            }
        }
    }
}
