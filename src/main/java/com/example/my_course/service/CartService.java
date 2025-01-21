package com.example.my_course.service;

//todo - study how to save student extend user in student repo without problem

import com.example.my_course.entity.*;
import com.example.my_course.repository.CartRepository;
import com.example.my_course.repository.CourseRepository;
import com.example.my_course.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class); // Declare the Logger

    private final CartRepository cartRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       CourseRepository courseRepository,
                       UserRepository userRepository
                       ) {
        this.cartRepository = cartRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public List<User> getUsersWithUserRole() {
        return userRepository.findByRole(Role.USER);
    }

    @Scheduled(fixedRate = 60000)
    public void createUserCart() {
        logger.info(":::Method createUserCart is called...");
        List<User> users = getUsersWithUserRole();

        for (int i=0;i<users.size();i++) {
            User user = users.get(i);
            Cart existingCart = cartRepository.findByStudentEmail(user.getEmail());

            if (existingCart == null) {
                Cart cart = new Cart();
                cart.setStudentEmail(user.getEmail());
                cart.setCartStatus(Cart.CartStatus.UNPAID);
                cart.setCreatedAt(LocalDateTime.now());
                cart.setUpdatedAt(LocalDateTime.now());
                cart.setStudent(user); // Setting the associated user
                cartRepository.save(cart);
                System.out.println("Created a new cart for: " + user.getEmail());
            } else {
                existingCart.setCreatedAt(existingCart.getCreatedAt());
                existingCart.setUpdatedAt(LocalDateTime.now());
                cartRepository.save(existingCart);
                System.out.println("Cart already exists for: " + user.getEmail());
            }
        }
    }
}
