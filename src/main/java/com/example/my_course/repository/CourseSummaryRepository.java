package com.example.my_course.repository;

import com.example.my_course.entity.CourseSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSummaryRepository  extends JpaRepository<CourseSummary, Long> {

}
