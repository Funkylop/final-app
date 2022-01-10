package com.hramyko.finalapp.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends User {

    public Admin() {
    }

    public Admin(User user) {
        super(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(),
                user.getCreatedAt(), user.getRole(), user.getStatus());
    }

    @Override
    public String toString() {
        return "Admin{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", role=" + role +
                '}';
    }
}
