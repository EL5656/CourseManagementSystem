package com.example.my_course.dto;

import java.util.Base64;

public class CourseDto {
    private int courseId;
    private String name;
    private String desc;
    private Double price;
    private String image; // get String from database
    private String firstName; // new field for firstName
    private String lastName; // new field for lastName
    private String email; // new field for email

    public CourseDto(int courseId, String name, String desc, Double price, byte[] imageBytes,
                     String firstName, String lastName, String email) {
        this.courseId = courseId;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.image = imageBytes != null ? Base64.getEncoder().encodeToString(imageBytes) : null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public CourseDto() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Existing getters and setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
