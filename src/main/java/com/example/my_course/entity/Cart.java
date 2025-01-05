package com.example.my_course.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    Long cartId;

    public Cart(Long cartId, List<Course> courses) {
        this.cartId = cartId;
        this.courses = courses;
    }

    public Cart() {
    }

    @OneToOne(mappedBy = "cart")
    private Student student;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses;

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", courses=" + courses +
                '}';
    }
}
