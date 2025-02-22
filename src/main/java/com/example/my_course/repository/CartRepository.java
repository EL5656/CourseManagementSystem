package com.example.my_course.repository;

import com.example.my_course.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByStudentEmail(String studentEmail);
    Cart findByStudentUid(Long uid);
    Cart findByCartId(Long cartId);
}
