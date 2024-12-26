package com.example.my_course.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class CourseSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @SequenceGenerator(name = "course_seq", sequenceName = "course_id_seq", allocationSize = 1)
    private Long id;

    private Long courseId;

    private Long lecturerId;
    private LocalDateTime latestUpdateTimestamp;

    public CourseSummary(Long id, Long courseId, Long lecturerId) {
        this.id = id;
        this.courseId = courseId;
        this.lecturerId = lecturerId;
        this.latestUpdateTimestamp = LocalDateTime.now();
    }

    public CourseSummary() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public LocalDateTime getLatestUpdateTimestamp() {
        return latestUpdateTimestamp;
    }

    public void setLatestUpdateTimestamp(LocalDateTime latestUpdateTimestamp) {
        this.latestUpdateTimestamp = latestUpdateTimestamp;
    }

    @Override
    public String toString() {
        return "CourseSummary{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", lecturerId=" + lecturerId +
                ", latestUpdateTimestamp=" + latestUpdateTimestamp +
                '}';
    }
}
