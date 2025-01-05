package com.example.my_course.controller;

import com.example.my_course.service.CourseSummaryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my_course_store/course")
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
