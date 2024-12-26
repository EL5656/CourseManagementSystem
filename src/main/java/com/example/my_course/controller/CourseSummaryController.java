package com.example.my_course.controller;

import com.example.my_course.entity.CourseSummary;
import com.example.my_course.service.CourseSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseSummaryController {
    private final CourseSummaryService courseSummaryService;
    public CourseSummaryController(CourseSummaryService courseSummaryService) {
        this.courseSummaryService = courseSummaryService;
    }
    @GetMapping("/generate")
    public String generateCourseSummary() {
        courseSummaryService.saveCourseSummary();  // Trigger saveCourseSummary method
        return "Course Summary Generation Started";
    }
}
