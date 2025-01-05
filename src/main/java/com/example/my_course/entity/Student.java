package com.example.my_course.entity;

import jakarta.persistence.*;

@Entity
public class Student extends User{

    public Student(String username, String email, String mobile, String password, Role role, Cart cart) {
        super(username, email, mobile, password, role);
        this.cart = cart;
    }

    public Student() {
    }

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Student{" +
                "cart=" + cart +
                '}'
                + super.toString();
    }


}
