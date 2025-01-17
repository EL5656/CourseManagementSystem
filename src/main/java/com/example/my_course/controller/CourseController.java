package com.example.my_course.controller;

import com.example.my_course.dto.CourseDto;
import com.example.my_course.entity.Course;
import com.example.my_course.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/my_course_store/courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/view")
    public ResponseEntity<List<Course>> getAllCourses(){
        return new ResponseEntity<>(courseService.getAllCourse(), HttpStatus.OK);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id){
        Course course = courseService.getCourseById(id);
        if(course != null){
            return new ResponseEntity<>(course,HttpStatus.OK);
        }
        return new ResponseEntity<>("Course is not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create_with_lecturer")
    public ResponseEntity<CourseDto> createCourseWithLecturer(
            @RequestParam(name="name") String name,
            @RequestParam(name="desc") String desc,
            @RequestParam(name="price") Double price,
            @RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(name="firstName") String firstName,
            @RequestParam(name="lastName") String lastName,
            @RequestParam(name="email") String email
    ) {
        try {
            Course course = courseService.createCourseWithLecturer(name, desc, price, imageFile, firstName, lastName, email);
            CourseDto courseDto = new CourseDto(
                    course.getCourseId().intValue(),  // Ensure courseId is cast correctly
                    course.getName(),
                    course.getDesc(),
                    course.getPrice(),
                    course.getImage() != null ? course.getImage().getBytes(1, (int) course.getImage().length()) : null,  // Handling image conversion
                    course.getLecturer() != null ? course.getLecturer().getFirstName() : null,  // Check for null
                    course.getLecturer() != null ? course.getLecturer().getLastName() : null,
                    course.getLecturer() != null ? course.getLecturer().getEmail() : null
            );
            return new ResponseEntity<>(courseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/create")
    public ResponseEntity<?> addCourse(@RequestBody Course newCourse){
        try{
            Course course = courseService.addCourse(newCourse);
            return new ResponseEntity<>(course, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable Long id,
                                               @RequestBody Course updatedDetails) {
        Course course = courseService.updateCourse(id, updatedDetails);
        if (course != null) {
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        Course course = courseService.getCourseById(id);
        if(course!=null){
            courseService.deleteCourse(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
    }

}
