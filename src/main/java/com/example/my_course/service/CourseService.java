package com.example.my_course.service;

import java.util.ArrayList;
import com.example.my_course.entity.Course;
import com.example.my_course.entity.Lecturer;
import com.example.my_course.repository.CourseRepository;
import com.example.my_course.repository.LecturerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;
    public CourseService(CourseRepository courseRepository, LecturerRepository lecturerRepository) {
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
    }
    private Long getLecturerId(Course course){
        return course.getLecturer().getLecturerId();
    }

    public List<String> getCourseSummaries() {
        List<Object[]> results = courseRepository.findCourseIdAndLecturerId();
        List<String> summaries = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            Object[] row = results.get(i);
            summaries.add("Course ID: " + row[0] + ", Lecturer ID: " + row[1]);
        }

        return summaries;
    }
    public Course addCourse(Course course){
        if(course.getLecturer()!=null && getLecturerId(course)!=null){
            Lecturer lecturer = lecturerRepository.findById(getLecturerId(course))
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Lecturer not found with ID: " + getLecturerId(course)));
            course.setLecturer(lecturer);
        }
        return courseRepository.save(course);
    }

    public List<Course> getAllCourse(){
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course updateCourse(Long id, Course updatedDetails){
        Course course = getCourseById(id);
        course.setName(updatedDetails.getName());
        course.setDesc(updatedDetails.getDesc());
        course.setPrice(updatedDetails.getPrice());

        if (updatedDetails.getLecturer() != null && getLecturerId(updatedDetails) != null) {
            Lecturer lecturer = lecturerRepository.findById(getLecturerId(updatedDetails))
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Lecturer not found with ID: " + getLecturerId(updatedDetails)));
            course.setLecturer(lecturer);
        }
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id){
        courseRepository.deleteById(id);
    }

}
