package com.example.my_course.controller;

import com.example.my_course.entity.Cart;
import com.example.my_course.entity.Course;
import com.example.my_course.repository.CartRepository;
import com.example.my_course.service.CartService;
import com.example.my_course.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    private final CartRepository cartRepository;
    private final CourseService courseService;
    private final CartService cartService;
    public CartController(CartRepository cartRepository,
                          CourseService courseService,
                          CartService cartService) {
        this.cartRepository = cartRepository;
        this.courseService = courseService;
        this.cartService = cartService;
    }

    @GetMapping("/getCartId")
    public ResponseEntity<?> getCartId(@RequestParam String userEmail) {
        Cart cart = cartRepository.findByStudentEmail(userEmail);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }
        return ResponseEntity.ok("{\"cartId\": " + cart.getCartId() + "}");
    }

    @GetMapping("/{cartId}/courses")
    public ResponseEntity<List<Course>> getCoursesByCart(@PathVariable Long cartId) {
        Cart cart = new Cart();
        cart.setCartId(cartId);
        List<Course> courses = courseService.findAllCoursesByCart(cart);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getCartTotal(@RequestParam String userEmail) {
        Cart cart = cartRepository.findByStudentEmail(userEmail);
        if (cart == null) {
            return ResponseEntity.badRequest().body(0.0);
        }
        return ResponseEntity.ok(cart.getTotalAmount());
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Cart> getCartByUid(@PathVariable Long uid) {
        Cart cart = cartService.getCartByStudentUid(uid);
        return ResponseEntity.ok(cart);
    }
}
