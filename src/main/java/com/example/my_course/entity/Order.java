package com.example.my_course.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String email;
    private Long cartId;
    private Double totalAmount;
    @Column(nullable = false)
    private String description;
    private LocalDateTime createdAt;
    private String orderStatus; // "PAID"
    private String paymentId;
    private LocalDateTime paidAt;

    public Order(Long orderId, String email, Long cartId, Double totalAmount, String description, LocalDateTime createdAt,
                 String orderStatus, String paymentId, LocalDateTime paidAt) {
        this.orderId = orderId;
        this.email = email;
        this.cartId = cartId;
        this.totalAmount = totalAmount;
        this.description = description;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
        this.paymentId = paymentId;
        this.paidAt = paidAt;
    }

    public Order(){}

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", email='" + email + '\'' +
                ", cartId=" + cartId +
                ", totalAmount=" + totalAmount +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", orderStatus='" + orderStatus + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", paidAt=" + paidAt +
                '}';
    }
}
