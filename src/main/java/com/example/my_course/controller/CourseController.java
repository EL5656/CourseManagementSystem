package com.example.my_course.controller;

import com.example.my_course.entity.Course;
import com.example.my_course.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/online_learning")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(){
        return new ResponseEntity<>(courseService.getAllCourse(), HttpStatus.OK);
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id){
        Course course = courseService.getCourseById(id);
        if(course != null){
            return new ResponseEntity<>(course,HttpStatus.OK);
        }
        return new ResponseEntity<>("Course is not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add_course")
    public ResponseEntity<?> addCourse(@RequestBody Course newCourse){
        try{
            Course course = courseService.addCourse(newCourse);
            return new ResponseEntity<>(course, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update_course/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable Long id,
                                               @RequestBody Course updatedDetails) {
        Course course = courseService.updateCourse(id, updatedDetails);
        if (course != null) {
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete_course/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        Course course = courseService.getCourseById(id);
        if(course!=null){
            courseService.deleteCourse(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
    }

}
