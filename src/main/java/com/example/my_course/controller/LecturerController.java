package com.example.my_course.controller;

import com.example.my_course.entity.Lecturer;
import com.example.my_course.service.LecturerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my_course_store/lecturer")
public class LecturerController {
    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addLecturer(@RequestBody Lecturer newLecturer){
        try{
            Lecturer lecturer = lecturerService.addLecturer(newLecturer);
            return new ResponseEntity<>(lecturer, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Lecturer>> getAllLecturer(){
        return new ResponseEntity<>(lecturerService.getAllLecturer(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLecturer(@PathVariable Long id){
        Lecturer lecturer = lecturerService.getLecturer(id);
        if(lecturer != null){
            return new ResponseEntity<>(lecturer,HttpStatus.OK);
        }
        return new ResponseEntity<>("Lecturer is not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLecturer(@PathVariable Long id, @RequestBody Lecturer updateLecturer){
        Lecturer lecturer = lecturerService.updateLecturer(id, updateLecturer);
        if(lecturer != null){
            return new ResponseEntity<>(lecturer,HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLecturer(@PathVariable Long id){
        Lecturer lecturer = lecturerService.getLecturer(id);
        if(lecturer!=null){
            lecturerService.deleteLecturer(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("Lecturer is not found", HttpStatus.NOT_FOUND);
    }
}
