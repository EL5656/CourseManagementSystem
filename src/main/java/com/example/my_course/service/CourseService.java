package com.example.my_course.service;

import com.example.my_course.entity.Cart;
import com.example.my_course.entity.Course;
import com.example.my_course.entity.Lecturer;
import com.example.my_course.repository.CourseRepository;
import com.example.my_course.repository.LecturerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.rowset.serial.SerialBlob;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;
    private final ItemService itemService;

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    public CourseService(CourseRepository courseRepository, LecturerRepository lecturerRepository,
                         ItemService itemService) {
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
        this.itemService = itemService;
    }
    private Long getLecturerId(Course course){
        return course.getLecturer().getLecturerId();
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

    public String getImageFileType(long id) {
        String fileExtension = "jpg"; // Hardcoded for illustration
        return fileExtension;
    }

    public byte[] getImageByCourse(long courseId) throws SQLException {
        Course course = courseRepository.findById(courseId).orElse(null);

        if (course != null && course.getImage() != null) {
            Blob imageBlob = course.getImage();
            return imageBlob.getBytes(1, (int) imageBlob.length());
        }

        return new byte[0]; // Return empty array instead of throwing an error
    }

    public Course updateCourse(
            long id, String name, String desc, Double price, MultipartFile imageFile,
            String firstName, String lastName, String email)
            throws IOException, SQLException {

        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));


        course.setName(name);
        course.setDesc(desc);
        course.setPrice(price);

        if (imageFile != null && !imageFile.isEmpty()) {
            byte[] imageBytes = imageFile.getBytes();
            Blob photoBlob = new SerialBlob(imageBytes);
            course.setImage(photoBlob);
        }

        Lecturer lecturer = course.getLecturer();

        if (lecturer == null || lecturer.getLecturerId() == 0L) {
            lecturer = lecturerRepository.findByFirstNameAndLastNameAndEmail(firstName, lastName, email);
            if (lecturer == null) {
                // If no matching lecturer is found, create a new one
                lecturer = new Lecturer();
            }
        }

        lecturer.setFirstName(firstName);
        lecturer.setLastName(lastName);
        lecturer.setEmail(email);

        lecturer = lecturerRepository.save(lecturer);
        course.setLecturer(lecturer);
        return courseRepository.save(course);
    }

    public Course createCourseWithLecturer(
            String name, String desc, Double price, MultipartFile imageFile,
            String firstName, String lastName, String email
    ) throws IOException, SQLException {

        System.out.println("Creating course with name: " + name);
        System.out.println("Description: " + desc);
        System.out.println("Price: " + price);
        System.out.println("Lecturer Info - First Name: " + firstName + ", Last Name: " + lastName + ", Email: " + email);

        // Check if the lecturer already exists
        Lecturer lecturer = lecturerRepository.findByEmail(email).orElse(null);
        if (lecturer == null) {
            lecturer = new Lecturer(firstName, lastName, email);
            lecturer = lecturerRepository.save(lecturer);
        }

        // Create course without checking for existing ID
        Course course = new Course(name, desc, price, lecturer);

        if (imageFile != null && !imageFile.isEmpty()) {
            byte[] imageBytes = imageFile.getBytes();
            Blob photoBlob = new SerialBlob(imageBytes);
            course.setImage(photoBlob);
        }

        // Save the course and return the saved entity
        Course savedCourse = courseRepository.save(course);
        System.out.println("Course saved successfully with ID: " + savedCourse.getCourseId());
        return savedCourse;
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

    public List<Course> findAllCoursesByCart(Cart cart){
        List<Long> courseIds = itemService.getCourseIdsByCart(cart);
        if (courseIds.isEmpty()) {
            return new ArrayList<>();
        }
        return courseRepository.findAllById(courseIds);
    }
}
