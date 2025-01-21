package com.example.my_course.repository;

import com.example.my_course.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByStudentEmail(String studentEmail);
}
