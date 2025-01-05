package com.example.my_course.dto;

import com.example.my_course.entity.Role;

public class RegisterRequest {
    private String username;
    private String email;
    private String mobile;
    private String password;
    private Role role;

    public RegisterRequest(String username, String email, String mobile, String password, Role role) {
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.role = role;
    }

    public RegisterRequest(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
