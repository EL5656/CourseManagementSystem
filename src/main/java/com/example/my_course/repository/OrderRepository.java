package com.example.my_course.repository;

import com.example.my_course.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findByPaymentId(String paymentId);
    public Order findByCartId(Long cartId);
}
