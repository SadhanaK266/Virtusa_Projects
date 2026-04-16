package models;

import java.time.LocalDate;

public class User {
    // Attributes
    private int userId;
    private String name;
    private String email;
    private String phone;
    private LocalDate membershipDate;
    private boolean isActive;

    // Constructor 1: For new user registration
    public User(String name, String email, String phone, LocalDate membershipDate) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipDate = membershipDate;
        this.isActive = true;  // New users are active by default
    }

    // Constructor 2: For retrieving from database
    public User(int userId, String name, String email, String phone,
                LocalDate membershipDate, boolean isActive) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipDate = membershipDate;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Email: %s | Phone: %s | Status: %s",
                userId, name, email, phone, isActive ? "Active" : "Inactive");
    }
}