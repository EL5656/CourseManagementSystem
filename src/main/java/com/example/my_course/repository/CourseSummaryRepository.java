package com.example.my_course.repository;

import com.example.my_course.entity.CourseSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseSummaryRepository  extends JpaRepository<CourseSummary, Long> {

}
