package com.hramyko.finalapp.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "traders")
@DiscriminatorValue("TRADER")
@PrimaryKeyJoinColumn(name = "user_id")
public class Trader extends User{

    @OneToMany(mappedBy = "trader")
    private List<GameObject> gameObjects;

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public Trader() {
    }

    public Trader(String email, String password, String firstName,
                  String lastName, Date createdAt, Role role, Status status, List<GameObject> gameObjects) {
        super(email, password, firstName, lastName, createdAt, role, status);
        this.gameObjects = gameObjects;
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
                '}';
    }
}
