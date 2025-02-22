package com.example.my_course.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="student_cart")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;
    private Long courseId;
    private Double price;

    @Column(nullable = false, updatable = false)
    private LocalDateTime addedAt;
    public Item() {}

    public Item(Cart cart, Long courseId, Double price,  LocalDateTime addedAt) {
        this.cart = cart;
        this.courseId = courseId;
        this.price = price;
        this.addedAt = addedAt;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", courseId=" + courseId +
                ", price=" + price +
                ", addedAt=" + addedAt +
                '}';
    }

}
