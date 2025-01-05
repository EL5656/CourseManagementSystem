package com.example.my_course.service;

import com.example.my_course.entity.Lecturer;
import com.example.my_course.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class LecturerService {
    private LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    public Lecturer addLecturer(Lecturer lecturer){
        return lecturerRepository.save(lecturer);
    }

    public List<Lecturer> getAllLecturer(){
        return lecturerRepository.findAll();
    }

    public Lecturer getLecturer(Long id){
        return lecturerRepository.findById(id).orElse(null);
    }

    public Lecturer updateLecturer(@PathVariable Long id, @RequestBody Lecturer updateLecturer){
        Lecturer lecturer = getLecturer(id);
        lecturer.setFirstName(updateLecturer.getFirstName());
        lecturer.setLastName(updateLecturer.getLastName());
        lecturer.setEmail(updateLecturer.getEmail());
        return lecturerRepository.save(lecturer);
    }

    public void deleteLecturer(Long id){
        lecturerRepository.deleteById(id);
    }
}
