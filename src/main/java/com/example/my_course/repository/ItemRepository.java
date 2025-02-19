package com.example.my_course.repository;

import com.example.my_course.entity.Cart;
import com.example.my_course.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByCartAndCourseId(Cart cart, Long courseId);
    List<Item> findByCart(Cart cart);
}
