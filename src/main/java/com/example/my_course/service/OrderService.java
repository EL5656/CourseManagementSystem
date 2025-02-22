package com.example.my_course.service;

import com.example.my_course.entity.Cart;
import com.example.my_course.entity.Order;
import com.example.my_course.repository.CartRepository;
import com.example.my_course.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    public Order saveOrder(Long cartId, String description, Double amount) {
        Order existingOrder = orderRepository.findByCartId(cartId);
        if (existingOrder != null) {
            existingOrder.setTotalAmount(amount);
            existingOrder.setDescription(description);
            return orderRepository.save(existingOrder);
        }

        Cart cart = cartRepository.findByCartId(cartId);
        if (cart == null) {
            throw new RuntimeException("Cart not found for cartId: " + cartId);
        }
        Order order = new Order();
        order.setEmail(cart.getStudentEmail());
        order.setCartId(cart.getCartId());
        order.setTotalAmount(cart.getTotalAmount());
        order.setDescription(description);
        order.setOrderStatus("Created");
        order.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order findByPaymentId(String paymentId) {
        Order order = orderRepository.findByPaymentId(paymentId);
        if (order == null) {
            throw new RuntimeException("Order not found for paymentId: " + paymentId);
        }
        return order;
    }

    public Order updateOrder(Long cartId, LocalDateTime orderPaid, String paymentId) {
        Order order = orderRepository.findByCartId(cartId);
        if (order == null) {
            throw new RuntimeException("Order not found for paymentId: " + cartId);
        }

        order.setPaymentId(paymentId);
        order.setPaidAt(orderPaid);
        order.setOrderStatus("PAID");

        return orderRepository.save(order);
    }
}
