package com.example.my_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Blob;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue
    private Long courseId ;
    @Column(name="course_name")
    private String name;
    @Column(name="course_desc")
    private String desc;
    @Column(name="course_price")
    private Double price;

    @Lob
    @JsonIgnore
    @Column(name = "course_image")
    private Blob image;

    public Course(String name, String desc, Double price, Lecturer lecturer) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.lecturer = lecturer;
    }

    public Course() {

    }
//
//    @ManyToOne
//    @JoinColumn(name = "cart_id")
//    private Cart cart;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "id"
    )
    private CourseSummary courseSummary;

//    public Cart getCart() {
//        return cart;
//    }
//
//    public void setCart(Cart cart) {
//        this.cart = cart;
//    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(
            name = "lecturer_id"
    )
    private Lecturer lecturer;

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                ", image=" + (image != null ? "Present" : "Not Present") +
                ", lecturerId=" + (lecturer != null ? lecturer.getLecturerId() : 0) +
                '}';
    }
}
