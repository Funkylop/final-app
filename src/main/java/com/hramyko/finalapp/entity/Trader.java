package com.hramyko.finalapp.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "traders")
public class Trader extends User{

    @OneToMany(mappedBy = "trader")
    private List<GameObject> gameObjects;

    private double rating;

    public Trader() {
    }

    public Trader(User user) {
        super(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(),
                user.getCreatedAt(), user.getRole(), user.getStatus());
        this.id = user.getId();
    }

    public Trader(String email, String password, String firstName,
                  String lastName, Date createdAt, Role role, Status status, List<GameObject> gameObjects) {
        super(email, password, firstName, lastName, createdAt, role, status);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Trader trader = (Trader) o;
        return Double.compare(trader.rating, rating) == 0 && Objects.equals(gameObjects, trader.gameObjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gameObjects, rating);
    }

    @Override
    public String toString() {
        return "Trader{" +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", role=" + role +
                ", rating=" + rating +
                '}';
    }
}
