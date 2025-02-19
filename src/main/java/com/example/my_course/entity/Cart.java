package com.example.my_course.entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;
//todo - may add cart total later

@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long cartId;

    private String studentEmail;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> item;
    private double totalAmount;

    public double getTotalAmount() { return totalAmount; }

    public void updateTotalAmount() {
        this.totalAmount = 0;
        if (item != null) {
            for (int i = 0; i < item.size(); i++) {
                this.totalAmount += item.get(i).getPrice();
            }
        }
    }
    public List<Item> getCartItems() { return item; }

    public void setCartItems(List<Item> item) {
        this.item = item;
        updateTotalAmount();
    }

    @OneToOne
    @JoinColumn(name = "student_uid", referencedColumnName = "uid")
    private User student;

    public Cart() {}

    public Cart(User student, CartStatus cartStatus) {
        this.student = student;
        this.cartStatus = cartStatus;
    }

    public Cart(String studentEmail, CartStatus cartStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.studentEmail = studentEmail;
        this.cartStatus = cartStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void updateTimestamps() {
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getCartId() {
        return cartId;
    }
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }
    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public CartStatus getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(CartStatus cartStatus) {
        this.cartStatus = cartStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public enum CartStatus {
        PAID,
        UNPAID
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", studentEmail='" + studentEmail + '\'' +
                ", cartStatus=" + cartStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
