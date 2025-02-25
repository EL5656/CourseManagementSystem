package com.example.my_course.controller;

import com.example.my_course.entity.Order;
import com.example.my_course.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(
            @RequestParam Long cartId, @RequestParam String description, @RequestParam Double amount
    ) {
        Order order = orderService.saveOrder(cartId, description, amount);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getOrderByEmail(@RequestParam String email) {
        Order order = orderService.getOrderByEmail(email);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getOrder(@PathVariable Long cartId) {
        Order order = orderService.findOrderByCartId(cartId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/paid")
    public ResponseEntity<Order> updateOrder(
            @RequestParam Long cartId,
            @RequestParam String paymentId) {

        Order updatedOrder = orderService.updateOrder(cartId, paymentId);
        return ResponseEntity.ok(updatedOrder);
    }
}
